package it.foodmood.persistence.inmemory;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import it.foodmood.domain.model.User;
import it.foodmood.domain.value.Email;
import it.foodmood.domain.value.Role;
import it.foodmood.persistence.dao.UserDao;

public class InMemoryUserDao extends AbstractInMemoryCrudDao<User, UUID> implements UserDao {
    
    private static InMemoryUserDao instance;

    private InMemoryUserDao() {
        // costruttore privato per singleton
    }

    public static synchronized InMemoryUserDao getInstance(){
        if(instance == null){
            instance = new InMemoryUserDao(); 
        }
        return instance;
    }

    @Override
    protected UUID getId(User user){
        return user.getId();
    }

    @Override
    public Optional<User> findByEmail(Email email){
        return storage.values().stream().filter(user -> user.getEmail().equals(email)).findFirst();
    }
    @Override
    public List<User> findByRole(Role role){
        return storage.values().stream().filter(user -> user.hasRole(role)).toList();
    }
}
