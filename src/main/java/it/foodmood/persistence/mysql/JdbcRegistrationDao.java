package it.foodmood.persistence.mysql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.foodmood.config.JdbcConnectionManager;
import it.foodmood.domain.model.Credential;
import it.foodmood.domain.model.User;
import it.foodmood.persistence.dao.RegistrationDao;
import it.foodmood.persistence.exception.PersistenceException;

public class JdbcRegistrationDao implements RegistrationDao {
    
    private static final String CALL_INSERT_USER = "{CALL save_user(?,?,?,?)}";
    private static final String CALL_SAVE_CREDENTIAL = "{CALL save_credential(?,?)}";
    private static final String CALL_EXISTS_BY_EMAIL= "{CALL exists_by_email(?)}";

    private static JdbcRegistrationDao instance;

    public static synchronized JdbcRegistrationDao getInstance(){
        if(instance == null){
            instance = new JdbcRegistrationDao();
        }
        return instance;
    }

    @Override
    public void saveUser(User user){
        try{
            Connection conn = JdbcConnectionManager.getInstance().getConnection();
            try(CallableStatement cs = conn.prepareCall(CALL_INSERT_USER)){
                cs.setString(1, user.getId().toString());
                cs.setString(2, user.getPerson().firstName());
                cs.setString(3, user.getPerson().lastName());
                cs.setString(4, user.getEmail().email());
                cs.setString(4, user.getRole().name());
                cs.execute();
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void saveCredential(Credential credential){
        try{
            Connection conn = JdbcConnectionManager.getInstance().getConnection();
            try(CallableStatement cs = conn.prepareCall(CALL_SAVE_CREDENTIAL)){
                cs.setString(1, credential.getUserId().toString());
                cs.setString(2, credential.getPasswordHash());
                cs.execute();
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public boolean existsByEmail(String email){
        try{
            Connection conn = JdbcConnectionManager.getInstance().getConnection();
            try(CallableStatement cs = conn.prepareCall(CALL_EXISTS_BY_EMAIL)){
                cs.setString(1, email.toLowerCase());
                boolean result = cs.execute();

                if(result){
                    try(ResultSet rs = cs.getResultSet()){
                        return rs.next(); // vero se c'è l'email
                    }
                }
                return false;
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }
}
