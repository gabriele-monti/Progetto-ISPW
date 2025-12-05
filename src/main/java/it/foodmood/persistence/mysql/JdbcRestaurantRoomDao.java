package it.foodmood.persistence.mysql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import it.foodmood.config.JdbcConnectionManager;
import it.foodmood.domain.model.RestaurantRoom;
import it.foodmood.domain.model.Table;
import it.foodmood.domain.value.TablePosition;
import it.foodmood.domain.value.TableStatus;
import it.foodmood.persistence.dao.RestaurantRoomDao;
import it.foodmood.persistence.exception.PersistenceException;

public class JdbcRestaurantRoomDao implements RestaurantRoomDao{
    private static JdbcRestaurantRoomDao instance;

    private static final String CALL_LOAD_RESTAURANT_ROOM = "{CALL load_restaurant_room()}";
    private static final String CALL_LOAD_RESTAURANT_TABLES = "{CALL load_restaurant_tables()}";
    private static final String CALL_SAVE_RESTAURANT_ROOM = "{CALL save_restaurant_room(?,?)}";
    private static final String CALL_DELETE_ALL_RESTAURANT_TABLES = "{CALL delete_all_restaurant_tables()}";
    private static final String CALL_INSERT_RESTAURANT_TABLE = "{CALL insert_restaurant_table(?,?,?,?,?)}";


    public static synchronized JdbcRestaurantRoomDao getInstance(){
        if(instance == null){
            instance = new JdbcRestaurantRoomDao();
        }
        return instance;
    }

    private JdbcRestaurantRoomDao(){
        // costruttore privato
    }

    @Override
    public Optional<RestaurantRoom> load(){
        try{
            Connection conn = JdbcConnectionManager.getInstance().getConnection();

            int rows;
            int cols;

            try (CallableStatement cs = conn.prepareCall(CALL_LOAD_RESTAURANT_ROOM)){
                ResultSet rs = cs.executeQuery();
                
                if(!rs.next()){
                    return Optional.empty();
                }

                rows = rs.getInt("room_rows");
                cols = rs.getInt("room_cols");
            }

            List<Table> tables = new ArrayList<>();

            try (CallableStatement cs = conn.prepareCall(CALL_LOAD_RESTAURANT_TABLES)){
                ResultSet rs = cs.executeQuery();
                
                while(rs.next()){
                    int id = rs.getInt("id");
                    int seats = rs.getInt("seats");
                    int row = rs.getInt("row");
                    int col = rs.getInt("col");
                    
                    String statusStr = rs.getString("status");
                    TableStatus status = TableStatus.valueOf(statusStr);

                    TablePosition position = new TablePosition(row, col);

                    Table table = new Table(id, seats, position, status);
                    tables.add(table);
                }
            }

            RestaurantRoom restaurantRoom = new RestaurantRoom(rows, cols, tables);
            return Optional.of(restaurantRoom);

        } catch (SQLException e) {
            throw new PersistenceException(e);
        }   
    }

    @Override
    public void save(RestaurantRoom restaurantRoom){
        try{
            Connection conn = JdbcConnectionManager.getInstance().getConnection();
            try(CallableStatement cs = conn.prepareCall(CALL_SAVE_RESTAURANT_ROOM)){
                cs.setInt(1, restaurantRoom.getRows());
                cs.setInt(2, restaurantRoom.getCols());
                cs.execute();
            }

            try(CallableStatement cs = conn.prepareCall(CALL_DELETE_ALL_RESTAURANT_TABLES)){
                cs.execute();
            }

            try(CallableStatement cs = conn.prepareCall(CALL_INSERT_RESTAURANT_TABLE)){
                for(Table table : restaurantRoom.getTables()){
                    cs.setInt(1, table.getId());
                    cs.setInt(2, table.getSeats());
                    cs.setInt(3, table.getPosition().getRow());
                    cs.setInt(4, table.getPosition().getCol());
                    cs.setString(5, table.getStatus().name());
                    cs.addBatch();
                }
                cs.executeBatch();
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }
}
