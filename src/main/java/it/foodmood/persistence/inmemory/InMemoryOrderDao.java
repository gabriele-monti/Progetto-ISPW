package it.foodmood.persistence.inmemory;

import java.util.UUID;

import it.foodmood.domain.model.Order;
import it.foodmood.persistence.dao.OrderDao;

public class InMemoryOrderDao extends AbstractInMemoryCrudDao<Order, UUID> implements OrderDao {
    
    private static InMemoryOrderDao instance;

    private InMemoryOrderDao(){
        // costruttore per il singleton
    }

    public static synchronized InMemoryOrderDao getInstance(){
        if(instance == null){
            instance = new InMemoryOrderDao();
        }
        return instance;
    }

    @Override
    public UUID getId(Order order){
        return order.getId();
    }
}
