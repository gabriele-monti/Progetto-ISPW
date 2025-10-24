package it.foodmood.persistence;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionProvider {
    /* Restitusico una connessione valida presa dal pool
     * i DAO useranno questa per comunicare con il database
    */

    Connection getConnection() throws SQLException;
}
