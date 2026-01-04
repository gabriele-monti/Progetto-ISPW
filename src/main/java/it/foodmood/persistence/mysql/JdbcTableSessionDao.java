package it.foodmood.persistence.mysql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Optional;
import java.util.UUID;

import it.foodmood.config.JdbcConnectionManager;
import it.foodmood.domain.model.TableSession;
import it.foodmood.exception.PersistenceException;
import it.foodmood.persistence.dao.TableSessionDao;

public class JdbcTableSessionDao implements TableSessionDao {

    private static final String CALL_ENTER_SESSION = "{CALL enter_table_session(?,?,?)}";
    private static final String CALL_CLOSE_SESSION = "{CALL close_table_session_by_table(?)}";

    private static JdbcTableSessionDao instance;

    private JdbcTableSessionDao(){
        // Costruttore vuoto
    }

    public static synchronized JdbcTableSessionDao getInstance(){
        if(instance == null) instance = new JdbcTableSessionDao();
        return instance;
    }
    
    @Override
    public UUID enterSession(TableSession tableSession){
        try{
            Connection conn = JdbcConnectionManager.getInstance().getConnection();
            try(CallableStatement cs = conn.prepareCall(CALL_ENTER_SESSION)){
                cs.setInt(1, tableSession.getTableId());
                cs.setString(2, tableSession.getTableSessionId().toString());
                cs.registerOutParameter(3, Types.CHAR);

                cs.execute();

                String sessionId = cs.getString(3);
                return UUID.fromString(sessionId);
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void closeSession(int tableId){
        try {
            Connection conn = JdbcConnectionManager.getInstance().getConnection();
            try(CallableStatement cs = conn.prepareCall(CALL_CLOSE_SESSION)){
                cs.setInt(1, tableId);

                cs.execute();
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public Optional<TableSession> findById(UUID tableSessionId){
        return null;
    }
}
