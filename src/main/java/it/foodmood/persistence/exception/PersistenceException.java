package it.foodmood.persistence.exception;

/*
 * Eccezioni per tutti gli errori di persistenza (DAO, DB, ...)
 * Sono uncheked in quanto non gestite dal chiamante
 */

public class PersistenceException extends RuntimeException{
    public PersistenceException(String message){ super(message);}
    public PersistenceException(String message, Throwable cause){ super(message, cause);}
    public PersistenceException(Throwable cause){ super(cause);}
}
