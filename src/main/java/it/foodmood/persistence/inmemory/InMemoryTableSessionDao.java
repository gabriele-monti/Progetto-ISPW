package it.foodmood.persistence.inmemory;

import java.util.Optional;
import java.util.UUID;

import it.foodmood.domain.model.TableSession;
import it.foodmood.persistence.dao.TableSessionDao;

public class InMemoryTableSessionDao implements TableSessionDao {
    
    private static InMemoryTableSessionDao instance;

    private InMemoryTableSessionDao(){
        // costruttore per il singleton
    }

    public static synchronized InMemoryTableSessionDao getInstance(){
        if(instance == null){
            instance = new InMemoryTableSessionDao();
        }
        return instance;
    }

    @Override
    public UUID enterSession(TableSession session){
        return null;
        // Funzionalità non implementata
    }

    @Override
    public void closeSession(int sessionId){
        // Funzionalità non implementata
    }

    @Override
    public Optional<TableSession> findById(UUID tableSessionId){
        return null;
    }

}
