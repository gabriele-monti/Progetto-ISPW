package it.foodmood.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

public final class DriverManagerConnection implements ConnectionProvider{
    
    private static volatile DriverManagerConnection instance;
    private  volatile Connection connection;
    
    private final String url;
    private final String user;
    private final String password;

    private DriverManagerConnection(String url, String user, String password){
        this.url = Objects.requireNonNull(url, "L'url non può essere nullo.");
        this.user = Objects.requireNonNull(user, "L'user non può essere nullo.");
        this.password = Objects.requireNonNull(password, "La password non può essere nulla.");

        Runtime.getRuntime().addShutdownHook(new Thread(this::closeSafely, "jdbc-shutdown-hook"));
    }

    public static  DriverManagerConnection init(String url, String user, String password){
        DriverManagerConnection local = instance;
        if(local == null){
            synchronized (DriverManagerConnection.class){
                local = instance;
                if(local == null){
                    instance = local = new DriverManagerConnection(url, user, password);
                }
            }
        } else {
            if( !local.url.equals(url) || !local.user.equals(user) || !local.password.equals(password)){
                throw new IllegalStateException("DriverManager già inizializzato con credenziali diverse!");
            }
        }
        return local;
    }

    public static DriverManagerConnection getInstance(){
        DriverManagerConnection local = instance;
        if(local == null){
            throw new IllegalStateException("Connessione non inizializzata. Chiama init(url,user,password)");
        }
        return local;
    }

    @Override
    public Connection getConnection() throws SQLException{
        Connection conn = connection;
        if(conn == null || conn.isClosed() || !isValid(conn)){
            synchronized (this){
                conn = connection;
                if(conn == null || conn.isClosed() || !isValid(conn)){
                    conn = DriverManager.getConnection(url, user, password);
                    conn.setAutoCommit(true);
                    connection = conn;
                }
            }
        }
        return conn;
    }

    @Override
    public synchronized void close(){
        Connection conn = connection;
        connection = null;
        if(conn != null){
            try{
                if (!conn.isClosed()){
                    conn.close();
                }
            } catch (SQLException _) {
                // da implementare
            }
        }
    }

    public void closeSafely(){
        try{
            close();
        } catch (Exception _) {
            // 
        }
    }

    private boolean isValid(Connection conn){
        try{
            return conn != null && conn.isValid(2);
        } catch (SQLException e) {
            return false;
        }
    }
}
