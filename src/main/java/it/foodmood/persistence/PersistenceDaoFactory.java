// package it.foodmood.persistence;

// import it.foodmood.config.PersistenceSettings;
// import it.foodmood.persistence.dao.DishDao;

// public abstract class PersistenceDaoFactory {

//     protected final PersistenceSettings settings;

//     protected PersistenceDaoFactory(PersistenceSettings settings){
//         this.settings = settings;
//     }
    
//     // Unica istanza di factory per creare famiglie di dao
//     private static PersistenceDaoFactory instance = null;

//     public static PersistenceDaoFactory getInstance(){
//         try{
//             // Se l'istanza non esiste viene creata
//             if(instance == null){
//                 Persistence mode = PersistenceSettings.getPersistenceMode();

//                 switch (mode) {
//                     case DEMO -> instance =  new InMemoryPersistenceFactory();
//                     case FULL -> instance = new MySqlPersistenceFactory();
            
//                     // default:

//                 }
//             }
//         } catch (Exception _){

//         }
//         return instance;
//     }

//     public abstract DishDao createDishDao();

// }
