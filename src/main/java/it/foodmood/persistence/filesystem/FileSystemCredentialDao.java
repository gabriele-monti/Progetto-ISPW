package it.foodmood.persistence.filesystem;

import java.util.List;
import java.util.UUID;

import it.foodmood.domain.model.Credential;
import it.foodmood.exception.PersistenceException;
import it.foodmood.persistence.dao.CredentialDao;

public class FileSystemCredentialDao extends AbstractCsvDao implements CredentialDao {

    private static FileSystemCredentialDao instance;
    private static final String SEPARATOR = ";";

    private FileSystemCredentialDao(){
        super(FileSystemPaths.CREDENTIALS);
    }

    public static synchronized FileSystemCredentialDao getInstance(){
        if(instance == null){
            instance = new FileSystemCredentialDao();
        }
        return instance;
    }

    @Override
    public void saveCredential(Credential credential){
        String line = toCsv(credential);
        appendLine(line);
    }

    @Override
    public Credential findByUserId(UUID userId){
        // leggo tutte le righe del csv
        List<String> lines = readAllLines();
        for(String line : lines){
            Credential credential = fromCsv(line);
            if(credential.getUserId().equals(userId)){
                return credential;
            }
        }
        return null;
    }


    private String toCsv(Credential credential){
        return credential.getUserId().toString() + SEPARATOR + 
               credential.getPasswordHash();
    }

    private Credential fromCsv(String line){
        String[] token = line.split(SEPARATOR);
        if(token.length != 2){
            throw new PersistenceException("Riga credenzili malformata: " + line);
        }
        UUID userId = UUID.fromString(token[0]);
        String passwordHash = token[1];

        return new Credential(userId, passwordHash);

    }
}
