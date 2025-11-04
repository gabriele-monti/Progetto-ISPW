package it.foodmood.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

public final class DriverManagerConnectionProvider implements ConnectionProvider {
    
    private final String url;
    private final String user;
    private final String password;

    public DriverManagerConnectionProvider(String url, String user, String password){
        this.url = Objects.requireNonNull(url, "L'url non può essere nullo.");
        this.user = Objects.requireNonNull(user, "L'user non può essere nullo.");
        this.password = Objects.requireNonNull(password, "La password non può essere nulla.");
    }

    @Override
    public Connection getConnection() throws SQLException{
        return DriverManager.getConnection(url, user, password);
    }

}
