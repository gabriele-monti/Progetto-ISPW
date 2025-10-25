package it.foodmood.persistence.mysql;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import it.foodmood.domain.model.Dish;
import it.foodmood.persistence.ConnectionProvider;
import it.foodmood.persistence.dao.DishDAO;
import it.foodmood.persistence.exception.PersistenceException;

public class JdbcDishDAO implements DishDAO {
    
    private final ConnectionProvider provider;

    public JdbcDishDAO(ConnectionProvider provider){
        this.provider = provider;
    }

    @Override
    public void save(Dish dish){
        try (Connection conn = provider.getConnection()){
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public Optional<Dish> findById(String id){
        try (Connection conn = provider.getConnection()){
            return Optional.empty();
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<Dish> findAll(){
        try (Connection conn = provider.getConnection()){
            return List.of();
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void deleteById(String id){
        try (Connection conn = provider.getConnection()){
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<Dish> findByCategory(String category){
        try (Connection conn = provider.getConnection()){
            return List.of();
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<Dish> findByDietCategory(String dietCategory){
        try (Connection conn = provider.getConnection()){
            return List.of();
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }
}
