package it.foodmood.persistence.inmemory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import it.foodmood.domain.model.Credential;
import it.foodmood.persistence.dao.CredentialDao;

public class InMemoryCredentialDao implements CredentialDao {

    private static InMemoryCredentialDao instance;

    private final Map<UUID, Credential> storage = new HashMap<>();

    private InMemoryCredentialDao(){}

    public static synchronized InMemoryCredentialDao getInstance(){
        if(instance == null){
            instance = new InMemoryCredentialDao();
        }
        return instance;
    }

    @Override
    public void saveCredential(Credential credential){
        storage.put(credential.userId(), credential);
    }

    @Override
    public Credential findByUserId(UUID userId){
        return storage.get(userId);
    }
}
