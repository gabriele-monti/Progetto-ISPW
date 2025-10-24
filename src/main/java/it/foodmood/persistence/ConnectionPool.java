package it.foodmood.persistence;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public final class ConnectionPool {
    private static volatile HikariDataSource dataSource;

    // Costruttore privato per impedire new ConnectionPoll()
    private ConnectionPool() {}

    public static void initialize(String url, String user, String pass){
        if(url == null || url.isBlank()){
            throw new IllegalArgumentException("L'url non può essere vuoto.");
        }
        if(dataSource != null) return;
        synchronized (ConnectionPool.class){
            if(dataSource == null){
                HikariConfig config = new HikariConfig();
                config.setJdbcUrl(url);
                config.setUsername(user);
                config.setPassword(pass);
                config.setMaximumPoolSize(5);
                dataSource = new HikariDataSource(config);

                Runtime.getRuntime().addShutdownHook(new Thread(ConnectionPool::shutdown));
            }
        }
    }

    // Restituisco il dataSource, lo useranno i DAO per ottenere la Connection
    public static DataSource getDataSource(){
        if(dataSource == null){
            throw new IllegalStateException("Connection pool non inizializzato correttamente!");
        }
        return dataSource;
    }

    // Chiudiamo il pool alla chiusura del software
    public static void shutdown(){
        HikariDataSource source = dataSource;
        if(source != null){
            synchronized(ConnectionPool.class){
                if(dataSource != null){
                    dataSource.close();
                    dataSource = null;
                }
            }
        }
    }

}
