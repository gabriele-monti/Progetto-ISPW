package it.foodmood.exception;

import it.foodmood.domain.value.OrderStatus;

public class OrderException extends DomainException {
    public OrderException(String message){
        super(message);
    }

    public OrderException(String action, OrderStatus status){
        super("Impossibile " + action + " l'ordine nello stato: " + status);
    }

    public static OrderException notModificable(OrderStatus status){
        return new OrderException("Ordine non moficabile nello stato: " + status);
    }

    public static OrderException emptyConfirm(){
        return new OrderException("Non puoi confermare un ordine vuoto");
    }

    public static OrderException cannotCancelServed(){
        return new OrderException("Non puoi cancellare un ordine già servito");
    }

    public static OrderException cannotServed(){
        return new OrderException("Possono essere serviti solo ordini confermati");
    }

    public static OrderException invalidQuantity(){
        return new OrderException("La quantità deve essere maggiore di zero");
    }

    public static OrderException invalidPrice(){
        return new OrderException("Il prezzo deve essere maggiore di zero");
    }
}
