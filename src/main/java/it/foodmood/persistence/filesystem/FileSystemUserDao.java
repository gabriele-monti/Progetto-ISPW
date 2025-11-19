package it.foodmood.persistence.filesystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import it.foodmood.domain.model.Customer;
import it.foodmood.domain.model.Manager;
import it.foodmood.domain.model.User;
import it.foodmood.domain.model.Waiter;
import it.foodmood.domain.value.Email;
import it.foodmood.domain.value.Person;
import it.foodmood.domain.value.Role;
import it.foodmood.persistence.dao.UserDao;
import it.foodmood.persistence.exception.PersistenceException;

public class FileSystemUserDao extends AbstractCsvDao implements UserDao {

    private static final String SEPARATOR = ";";

    private static FileSystemUserDao instance;

    private FileSystemUserDao(){
        super(FileSystemPaths.USERS);
    }

    public static synchronized FileSystemUserDao getInstance(){
        if(instance == null){
            instance = new FileSystemUserDao();
        }
        return instance;
    }

    @Override
    public void insert(User user){
        String line = toCsv(user);
        appendLine(line);
    }

    /* findAll() legge tutte le righe del csv, le converte in oggetti User e restituisce List<User>
    converto la lista in uno stream e applico il filtro per cercare l'id
    */

    @Override
    public Optional<User> findById(UUID id){
        return findAll().stream().filter(user -> user.getId().equals(id)).findFirst();
    }

    @Override
    public List<User> findAll(){
        List<String> lines = readAllLines();
        List<User> users = new ArrayList<>();
        for(String line: lines){
            users.add(fromCsv(line));
        }
        return users;
    }

    @Override
    public void deleteById(UUID id){
        List<User> all = findAll();
        boolean removed = all.removeIf(user -> user.getId().equals(id));
        if(!removed){
            return;
        }

        List<String> lines = new ArrayList<>();
        for(User user : all){
            lines.add(toCsv(user));
        }
        overwriteAllLines(lines);
    }

    @Override
    public Optional<User> findByEmail(Email email){
        return findAll().stream().filter(user -> user.getEmail().equals(email)).findFirst();
    }

    @Override
    public List<User> findByRole(Role role){
        List<User> out = new ArrayList<>();
        for(User user : findAll()){
            if(user.hasRole(role)){
                out.add(user);
            }
        }
        return out;
    }

    private String toCsv(User user){
        Person person = user.getPerson();
        return user.getId().toString() + SEPARATOR + person.firstName() + SEPARATOR +
               person.lastName() + SEPARATOR + user.getEmail().email() + SEPARATOR +
               user.getRole().name();
    }

    private User fromCsv(String line){
        String[] token = line.split(SEPARATOR);
        if(token.length != 5){
            throw new PersistenceException("Riga dell'utente malformata: " + line);
        }

        UUID id = UUID.fromString(token[0]);
        String firstName = token[1];
        String lastName = token[2];
        String emailStr = token[3];
        String roleStr = token[4];

        Person person = new Person(firstName, lastName);
        Email email = new Email(emailStr);
        Role role = Role.valueOf(roleStr);

        return switch (role){
            case CUSTOMER -> new Customer(id, person, email);
            case WAITER -> new Waiter(id, person, email);
            case MANAGER -> new Manager(id, person, email);
        };

    }


}
