package it.foodmood.persistence.inmemory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import it.foodmood.domain.model.TableSession;
import it.foodmood.persistence.dao.TableSessionDao;

public class InMemoryTableSessionDao implements TableSessionDao {
    
    private static InMemoryTableSessionDao instance;
    private final Map<UUID, TableSession> sessions = new HashMap<>();
    private final Map<Integer, UUID> tableToSession = new HashMap<>();

    private InMemoryTableSessionDao(){
        // costruttore privato per il singleton
    }

    public static synchronized InMemoryTableSessionDao getInstance(){
        if(instance == null){
            instance = new InMemoryTableSessionDao();
        }
        return instance;
    }

    @Override
    public UUID enterOrGetOpenSession(TableSession session){
        if(session == null){
            throw new IllegalArgumentException("La sessione non può essere nulla");
        }

        UUID sessionId = session.getTableSessionId();
        sessions.put(sessionId, session);
        tableToSession.put(session.getTableId(), sessionId);

        return sessionId;
    }

    @Override
    public void closeSession(int sessionId){
        // Funzionalità non implementata
    }

    @Override
    public Optional<TableSession> findById(UUID tableSessionId){
        if(tableSessionId == null){
            return Optional.empty();
        }

        return Optional.ofNullable(sessions.get(tableSessionId));
    }
}
