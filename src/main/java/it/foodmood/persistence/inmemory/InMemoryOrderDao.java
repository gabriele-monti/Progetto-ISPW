package it.foodmood.persistence.inmemory;

import java.util.List;
import java.util.Optional;
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

    @Override
    public void update(Order order){
        // Funzionalità non implememntata
    }

    @Override
    public List<Order> findByTableSessionId(UUID tableSessionId){
        return null;
    }

    @Override
    public Optional<Order> findOpen(UUID tableSessionId, UUID actorId){
        return null;
    }
}
