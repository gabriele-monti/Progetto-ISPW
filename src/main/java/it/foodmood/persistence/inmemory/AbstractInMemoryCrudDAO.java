package it.foodmood.persistence.inmemory;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import it.foodmood.persistence.dao.CrudDAO;

public abstract class AbstractInMemoryCrudDAO <T, I> implements CrudDAO<T,I> {
    protected final Map<I, T> storage = new ConcurrentHashMap<>();

    protected abstract I getId(T entity);

    @Override
    public void save(T entity){
        Objects.requireNonNull(entity, "L'entità non può essere nulla.");
        I id = Objects.requireNonNull(getId(entity), "L'ID non può essere nullo.");
        storage.put(id, entity);
    }

    @Override
    public Optional<T> findById(I id){
        if (id == null) return Optional.empty();
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<T> findAll(){
        return List.copyOf(storage.values());
    }

    @Override
    public void deleteById(I id){
        if (id == null) return;
        storage.remove(id);
    }
    
}
