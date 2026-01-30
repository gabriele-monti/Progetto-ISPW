package it.foodmood.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import it.foodmood.domain.model.Cart;
import it.foodmood.domain.model.CartItem;
import it.foodmood.domain.model.Dish;
import it.foodmood.domain.model.Order;
import it.foodmood.domain.model.OrderLine;
import it.foodmood.domain.model.TableSession;
import it.foodmood.exception.OrderException;
import it.foodmood.exception.PersistenceException;
import it.foodmood.exception.SessionExpiredException;
import it.foodmood.persistence.dao.DaoFactory;
import it.foodmood.persistence.dao.DishDao;
import it.foodmood.persistence.dao.OrderDao;
import it.foodmood.persistence.dao.TableSessionDao;
import it.foodmood.utils.SessionManager;

public class CustomerOrderController {
    
    private final OrderDao orderDao;
    private final DishDao dishDao;
    private final TableSessionDao tableSessionDao;
    private final SessionManager sessionManager;

    public CustomerOrderController(){
        DaoFactory factory = DaoFactory.getInstance();
        this.sessionManager = SessionManager.getInstance();
        this.orderDao = factory.getOrderDao();
        this.dishDao = factory.getDishDao();
        this.tableSessionDao = factory.getTableSessionDao();
    }

    public String createOrder(String sessionId) throws OrderException{
        if(sessionId == null || sessionId.isBlank()) {
            throw new OrderException("Sessione tavolo non valida");
        }

        try {
            UUID actorId = sessionManager.getCurrentActor();
            UUID tableSessionId = UUID.fromString(sessionId);

            TableSession tableSession = tableSessionDao.findById(tableSessionId).orElseThrow(() -> new OrderException("Sessione tavolo non valida"));

            if(!tableSession.isOpen()){
                throw new OrderException("Sessione tavolo chiusa, non è possibile effettuare l'ordine.");
            }

            Cart cart = getCart();

            if(cart.isEmpty()){
                throw new OrderException("Il carrello è vuoto");
            }

            List<OrderLine> orderLines = new ArrayList<>();

            for(CartItem line: cart.getItems()){
                if(line == null){
                    throw new OrderException("Riga ordine nulla");
                }
                
                Dish dish = dishDao.findById(line.getDishId()).orElseThrow(() -> new OrderException("Piatto non trovato"));

                orderLines.add(new OrderLine(dish.getId(), dish.getName(), dish.getPrice(), line.getQuantity()));
            }

            Order order = new Order(actorId, tableSessionId, orderLines);

            orderDao.insert(order);
            
            cart.clear();

            return order.getId().toString();

        } catch (IllegalArgumentException _){
            throw new OrderException("Dati ordine non validi");
        } catch (PersistenceException e){
            throw new OrderException("Errore tecnico nell'inserimento dell'ordine. Riprova più tardi", e);
        } catch (SessionExpiredException e){
            throw new OrderException(e.getMessage());
        }
    }

    private Cart getCart() throws OrderException{
        try {
            return sessionManager.getCart();
        } catch (SessionExpiredException e) {
            throw new OrderException(e.getMessage());
        }
    }
}
