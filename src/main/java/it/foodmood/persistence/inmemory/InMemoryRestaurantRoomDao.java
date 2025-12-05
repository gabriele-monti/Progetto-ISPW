package it.foodmood.persistence.inmemory;

import java.util.Optional;

import it.foodmood.domain.model.RestaurantRoom;
import it.foodmood.persistence.dao.RestaurantRoomDao;

public class InMemoryRestaurantRoomDao implements RestaurantRoomDao {
    private static InMemoryRestaurantRoomDao instance;

    public static synchronized InMemoryRestaurantRoomDao getInstance(){
        if(instance == null){
            instance = new InMemoryRestaurantRoomDao();
        }
        return instance;
    }

    @Override
    public Optional<RestaurantRoom> load(){
        System.out.println("Funzionalità non ancora implementata");
        return Optional.empty();
    }

    @Override
    public void save(RestaurantRoom restaurantRoom){
        System.out.println("Funzionalità non ancora implementata");
    }
}
