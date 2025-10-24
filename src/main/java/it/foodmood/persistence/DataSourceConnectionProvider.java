package it.foodmood.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

/*
 * Con questa classe permetto ai DAO di ottenere la Connection senza conoscere Hikari o JDBC
 * Si applica il patter GoF Adapter, il quale adatta DataSource all'interfaccia ConnectionProvider
 */

public final class DataSourceConnectionProvider implements ConnectionProvider {

    private final DataSource dataSource;

    public DataSourceConnectionProvider(DataSource dataSource){
        this.dataSource = dataSource;
    }
    
    // I DAO chiameranno questo metodo ogni volta che dovranno accedere al DB
    @Override
    public Connection getConnection() throws SQLException{
        return dataSource.getConnection(); // Prendo la connessione dal pool
    }
}
