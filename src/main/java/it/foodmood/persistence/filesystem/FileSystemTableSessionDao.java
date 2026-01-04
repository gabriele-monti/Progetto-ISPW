package it.foodmood.persistence.filesystem;

import java.util.Optional;
import java.util.UUID;

import it.foodmood.domain.model.TableSession;
import it.foodmood.persistence.dao.TableSessionDao;

public class FileSystemTableSessionDao extends AbstractCsvDao implements TableSessionDao {
    
    private static FileSystemTableSessionDao instance;

    public static synchronized FileSystemTableSessionDao getInstance(){
        if(instance == null){
            instance = new FileSystemTableSessionDao();
        }
        return instance;
    }

    private FileSystemTableSessionDao(){
        super(FileSystemPaths.TABLE_SESSION);
    }

    @Override
    public UUID enterSession(TableSession session){
        return null;
        // Funzionalità non implementata
    }

    @Override
    public void closeSession(int tableId){
        // Funzionalità non implementata
    }

    @Override
    public Optional<TableSession> findById(UUID tableSessionId){
        return null;
    }
}
