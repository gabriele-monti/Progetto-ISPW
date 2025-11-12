// package it.foodmood.controller.application;

// import java.util.Arrays;
// import java.util.Optional;

// import it.foodmood.bean.AuthenticationBean;
// import it.foodmood.bean.LoginBean;
// import it.foodmood.config.UserMode;
// import it.foodmood.domain.model.Credential;
// import it.foodmood.domain.model.User;
// import it.foodmood.domain.value.Email;
// import it.foodmood.domain.value.Role;
// import it.foodmood.exception.AuthenticationException;
// import it.foodmood.persistence.dao.CredentialDao;
// import it.foodmood.persistence.dao.DaoFactory;
// import it.foodmood.persistence.dao.UserDao;
// import it.foodmood.utils.Session;
// import it.foodmood.utils.SessionManager;
// import it.foodmood.utils.security.PasswordHasher;


// public class Login {

//     private final DaoFactory daoFactory;
//     private final CredentialDao credentialDao = DaoFactory.getInstance().getCredentialDao();
//     private final PasswordHasher passwordHasher = new PasswordHasher();
    

//     public AuthenticationBean loginUser(LoginBean loginBean, UserMode mode) throws AuthenticationException{
        
//         // 1) Verifichiamo l'esistenza dell'utente
//         Email email = new Email(loginBean.getEmail());
//         Optional<? extends User> optionalUser;

//         switch(mode){
//             case CUSTOMER -> optionalUser = daoFactory.getCustomerDao().findByEmail(email);
//             case WAITER -> optionalUser = daoFactory.getWaiterDao().findByEmail(email);
//             case MANAGER -> optionalUser = daoFactory.getManagerDao().findByEmail(email);
//             default -> throw new AuthenticationException("Credenziali errate.");
//         };

//         if(optionalUser.isEmpty()) throw new AuthenticationException("Credenziali errate.");

//         User user = optionalUser.get();

//         // 2) Carichiamo le credenziali
//         Credential credential = credentialDao.findByUserId(user.getId());
//         if(credential == null || credential.getPasswordHash() == null){
//             throw new AuthenticationException("Credenziali errate.");
//         }

//         // 3) Verifica della password
//         boolean valid = passwordHasher.verify(loginBean.getPassword(), credential.getPasswordHash());
//         char[] password = loginBean.getPassword();
//         if(password != null){
//             Arrays.fill(password, '\0');
//         }

//         if(!valid){
//             throw new AuthenticationException("Credenziali errate.");
//         }

//         // 4) Creo la sessione per l'utente
//         Session session = SessionManager.getInstance().createSession(user);
//         return new AuthenticationBean(session.getToken(), user.getRole());
//     }
// }