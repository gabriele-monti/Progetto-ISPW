package it.foodmood.domain.value;

import it.foodmood.exception.OrderException;

public enum OrderStatus {
    OPEN("APERTO"){
        @Override
        public OrderStatus confirm(){
            return CONFIRMED;
        }

        @Override
        public OrderStatus cancel(){
            return CANCELLED;
        }
    },
    CONFIRMED("CONFERMATO"){
        @Override
        public OrderStatus serve(){
            return SERVED;
        }

        @Override
        public OrderStatus cancel(){
            return CANCELLED;
        }
    },
    SERVED("SERVITO"),
    CANCELLED("CANCELLATO");

    private final String description;

    OrderStatus(String description){
        this.description = description;
    }

    public String description(){
        return description;
    }

    public OrderStatus confirm(){
        throw invalid("confermare", this);
    }

    public OrderStatus cancel(){
        throw invalid("cancellare", this);
    }

    public OrderStatus serve(){
        throw invalid("servire", this);
    }

    public boolean isPayable(){
        return this == SERVED;
    }

    public boolean isCancelled(){
        return this == CANCELLED;
    }

    public boolean isOpen(){
        return this == OPEN;
    }

    private static RuntimeException invalid(String action, OrderStatus status){
        return new OrderException(action, status);
    }
}
