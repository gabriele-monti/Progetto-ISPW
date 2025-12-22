package it.foodmood.exception;

public class TableSessionException extends DomainException{
    public TableSessionException(String message){
        super(message);
    }

    public static TableSessionException closed(){
        return new TableSessionException("Sessione tavolo chiusa");
    }

    public static TableSessionException tableFull(){
        return new TableSessionException("Tavolo pieno");
    }

    public static TableSessionException guestNotPresent(int guestNo){
        return new TableSessionException("Utente: " + guestNo + " non presente in sessione");
    }

    public static TableSessionException hadOpenOrders(){
        return new TableSessionException("Hai ordini ancora aperti");
    }

    public static TableSessionException customerIdRequired(){
        return new TableSessionException("ID cliente obbligatorio");
    }

    public static TableSessionException customerAlredyLinked(){
        return new TableSessionException("Cliente già associato a questa sessione");
    }

    public static TableSessionException invalidCovers(){
        return new TableSessionException("Il numero dei coperti deve essere maggiore di zero");
    }
}

