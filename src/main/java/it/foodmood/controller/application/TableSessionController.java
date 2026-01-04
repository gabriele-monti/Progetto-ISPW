package it.foodmood.controller.application;

import java.util.Optional;
import java.util.UUID;

import it.foodmood.domain.model.Table;
import it.foodmood.domain.model.TableSession;
import it.foodmood.exception.TableException;
import it.foodmood.exception.TableSessionException;
import it.foodmood.persistence.dao.DaoFactory;
import it.foodmood.persistence.dao.RestaurantRoomDao;
import it.foodmood.persistence.dao.TableSessionDao;

public class TableSessionController {

    private final TableSessionDao tableSessionDao;
    private final RestaurantRoomDao restaurantRoomDao;

    public TableSessionController(){
        DaoFactory factory = DaoFactory.getInstance();
        this.tableSessionDao = factory.getTableSessionDao();
        this.restaurantRoomDao = factory.getRestaurantRoomDao();
    }

    // L'utente inserisce il numero del tavolo
    // Se customerId == null -> utente guest
    public UUID enterSession(int tableId){

        if(tableId <= 0) throw new IllegalArgumentException("Il numero del tavolo deve essere maggiore di zero");

        Optional<Table> tableOpt = restaurantRoomDao.findById(tableId);
        // Verifico esistenza tavolo
        if(!tableOpt.isPresent()){ 
            throw new TableException("Numero " + tableId + " non associato a nessun tavolo");
        }

        try {
            TableSession tableSession = TableSession.create(tableId);

            UUID sessionId = tableSessionDao.enterSession(tableSession);

            return sessionId;

        } catch (IllegalArgumentException e) {
            throw new TableSessionException("Errore durante l'ingresso in sessione: " + e.getMessage());
        }
    }

    public void closeSession(int tableId){
        Optional<Table> tableOpt = restaurantRoomDao.findById(tableId);
        if(tableOpt.isEmpty()){ 
            throw new TableException("Numero " + tableId + " non associato a nessun tavolo");
        }
        
        try{        
            tableSessionDao.closeSession(tableId);
        } catch (IllegalArgumentException e){
            throw new TableSessionException("Errore durante la chiusura della sessione: " + e.getMessage());
        }
    }
}
