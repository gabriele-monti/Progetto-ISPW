package it.foodmood.persistence.filesystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import it.foodmood.domain.model.RestaurantRoom;
import it.foodmood.domain.model.Table;
import it.foodmood.domain.value.TablePosition;
import it.foodmood.domain.value.TableStatus;
import it.foodmood.exception.PersistenceException;
import it.foodmood.persistence.dao.RestaurantRoomDao;

public class FileSystemRestaurantRoomDao extends AbstractCsvDao implements RestaurantRoomDao{
    private static FileSystemRestaurantRoomDao instance;
    private static final String SEPARATOR = ";";

    public static synchronized FileSystemRestaurantRoomDao getInstance(){
        if(instance == null){
            instance = new FileSystemRestaurantRoomDao();
        }
        return instance;
    }

    private FileSystemRestaurantRoomDao(){
        super(FileSystemPaths.RESTAURANT_ROOM);
    }

    @Override
    public Optional<RestaurantRoom> load(){
        System.out.println("Funzionalità non ancora implementata");
        return Optional.empty();
    }

    @Override
    public void save(RestaurantRoom restaurantRoom){
        System.out.println("Funzionalità non ancora implementata");
    }

    @Override
    public Optional<Table> findById(Integer tableId){
        if(tableId == null) {
            return Optional.empty();
        }
        return findAll().stream().filter(table -> tableId.equals(table.getId())).findFirst();
    }

    @Override
    public List<Table> findAll(){
        List<String> lines = readAllLines();
        List<Table> tables = new ArrayList<>();
        for(String line: lines){
            tables.add(fromCsv(line));
        }
        return tables;
    }

    // @Override
    // public void deleteById(Integer tableId){
    //     if(tableId == null) {
    //         return;
    //     }

    //     List<Table> all = findAll();
    //     boolean removed = all.removeIf(table -> tableId.equals(table.getId()));
    //     if(!removed){
    //         return;
    //     }

    //     List<String> lines = new ArrayList<>();
    //     for(Table table : all){
    //         lines.add(toCsv(table));
    //     }
    //     overwriteAllLines(lines);
    // }

    // private String toCsv(Table table){
        
    //     return table.getId() + SEPARATOR + table.getSeats() + SEPARATOR + table.getPosition().getRow()
    //            + SEPARATOR + table.getPosition().getCol() + SEPARATOR + table.getStatus().name();
    // }

    private Table fromCsv(String line){
        String[] token = line.split(SEPARATOR, -1);

        if(token.length != 5){
            throw new PersistenceException("Riga tavolo malformata: " + line);
        }

        try {     
            
            int tableId = Integer.parseInt(token[0].trim());
            int seats = Integer.parseInt(token[1].trim());
            int row = Integer.parseInt(token[2].trim());
            int col = Integer.parseInt(token[3].trim());
           
            String tableStautsStr = token[4].trim();
            TableStatus status = TableStatus.valueOf(tableStautsStr);

            TablePosition position = new TablePosition(row, col);
            
            return new Table(tableId, seats, position, status);

        } catch (Exception e) {
            throw new PersistenceException("Errore durante il parsing della riga: " + line);
        }
    }

}
