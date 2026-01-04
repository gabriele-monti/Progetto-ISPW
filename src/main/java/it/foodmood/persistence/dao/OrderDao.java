package it.foodmood.persistence.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import it.foodmood.domain.model.Order;

public interface OrderDao extends CrudDao<Order, UUID> {
    void update(Order order);
    Optional<Order> findOpen(UUID tableSessionId, UUID actorId);
    List<Order> findByTableSessionId(UUID tableSessionId);
}
