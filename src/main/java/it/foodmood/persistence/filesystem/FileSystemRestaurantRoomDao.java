package it.foodmood.persistence.filesystem;

import java.util.Optional;

import it.foodmood.domain.model.RestaurantRoom;
import it.foodmood.persistence.dao.RestaurantRoomDao;

public class FileSystemRestaurantRoomDao implements RestaurantRoomDao{
    private static FileSystemRestaurantRoomDao instance;

    public static synchronized FileSystemRestaurantRoomDao getInstance(){
        if(instance == null){
            instance = new FileSystemRestaurantRoomDao();
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
