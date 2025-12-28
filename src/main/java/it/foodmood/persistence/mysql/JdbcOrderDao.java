package it.foodmood.persistence.mysql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import it.foodmood.config.JdbcConnectionManager;
import it.foodmood.domain.model.Order;
import it.foodmood.exception.PersistenceException;
import it.foodmood.persistence.dao.OrderDao;

public class JdbcOrderDao implements OrderDao {
        
    private static final String CALL_INSERT_ORDER = "{CALL insert_order(?)}";
    private static final String CALL_GET_ORDER_BY_ID = "{CALL get_order_by_id(?)}";
    private static final String CALL_GET_ALL_ORDERS = "{CALL get_all_orders()}";

    // Unica istanza di dao del Order che usa jdbc
    private static JdbcOrderDao instance;

    public static synchronized JdbcOrderDao getInstance(){
        if(instance == null){
            instance = new JdbcOrderDao();
        }
        return instance;
    }

    @Override
    public void insert(Order order){
        try {
            Connection conn = JdbcConnectionManager.getInstance().getConnection();
            try (CallableStatement cs = conn.prepareCall(CALL_INSERT_ORDER)){
                cs.setString(1, order.getId().toString());
                cs.execute();
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public Optional<Order> findById(UUID id){
        try{
            Connection conn = JdbcConnectionManager.getInstance().getConnection();
            return executeFindById(conn, id);
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    private Optional<Order> executeFindById(Connection conn, UUID id) throws SQLException{
        try (CallableStatement cs = conn.prepareCall(CALL_GET_ORDER_BY_ID)){
            cs.setString(1, id.toString());
            try(ResultSet rs = cs.executeQuery()){
                if(rs.next()){
                    Order order = mapRowToOrder(rs);

                    return Optional.of(order);
                } else {
                    return Optional.empty();
                }
            }
        }
    }

    @Override
    public List<Order> findAll(){
        try{
            Connection conn = JdbcConnectionManager.getInstance().getConnection();
            try (CallableStatement cs = conn.prepareCall(CALL_GET_ALL_ORDERS)) {
                ResultSet rs = cs.executeQuery();

                List<Order> orders = new ArrayList<>();

                while (rs.next()) {
                    Order order = mapRowToOrder(rs);

                    orders.add(order);
                }
                return orders;
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void deleteById(UUID id){
        try{
            Connection conn = JdbcConnectionManager.getInstance().getConnection();
            try (CallableStatement cs = conn.prepareCall(CALL_GET_ORDER_BY_ID)){
                cs.setString(1, id.toString());
                cs.execute();
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    private Order mapRowToOrder(ResultSet rs) throws SQLException {

        return null;
    }
}
