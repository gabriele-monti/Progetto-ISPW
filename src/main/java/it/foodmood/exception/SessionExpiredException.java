package it.foodmood.exception;

public class SessionExpiredException extends RuntimeException{
    public SessionExpiredException(){
        super("Sessione scaduta");
    }
}