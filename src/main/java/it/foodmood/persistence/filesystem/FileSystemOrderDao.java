package it.foodmood.persistence.filesystem;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import it.foodmood.domain.model.Order;

import it.foodmood.persistence.dao.OrderDao;

public class FileSystemOrderDao extends AbstractCsvDao implements OrderDao {
    
    private static FileSystemOrderDao instance;

    public static synchronized FileSystemOrderDao getInstance(){
        if(instance == null){
            instance = new FileSystemOrderDao();
        }
        return instance;
    }

    private FileSystemOrderDao(){
        super(FileSystemPaths.ORDERS);
    }


    @Override
    public void insert(Order order){
    }

    @Override
    public Optional<Order> findById(UUID id){
        return null;
    }

    @Override
    public List<Order> findAll(){
        return null;
    }

    @Override
    public void deleteById(UUID id){
        // Funzionalità non implememntata
    }
}
