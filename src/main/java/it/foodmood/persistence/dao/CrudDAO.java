package it.foodmood.persistence.dao;

import java.util.List;
import java.util.Optional;

public interface CrudDAO<T,I> {
    void save(T entity);
    Optional<T> findById(I id);
    List<T> findAll();
    void deleteById(I id);
}
