// package it.foodmood.persistence.mysql;

// import java.sql.Connection;
// import java.sql.SQLException;
// import java.util.Optional;
// import java.util.UUID;

// import it.foodmood.config.JdbcConnectionManager;
// import it.foodmood.domain.model.Dish;
// import it.foodmood.domain.model.Table;
// import it.foodmood.exception.PersistenceException;

// public class JdbcTableDao {


//     @Override
//     public Optional<Table> findById(int tableId){
//         try{
//             Connection conn = JdbcConnectionManager.getInstance().getConnection();
//             return executeFindById(conn, tableId);
//         } catch (SQLException e) {
//             throw new PersistenceException(e);
//         }
//     }
    
// }
