package it.foodmood.persistence.dao;

import java.util.List;
import java.util.Optional;

public interface CrudDAO<T, ID> {
    void save(T entity);
    Optional<T> findById(ID id);
    List<T> findAll();
    void deleteByID(ID id);
}
