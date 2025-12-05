package it.foodmood.persistence.dao;

import java.util.Optional;

import it.foodmood.domain.model.RestaurantRoom;

public interface RestaurantRoomDao {
    Optional<RestaurantRoom> load();

    void save(RestaurantRoom restaurantRoom);
}
