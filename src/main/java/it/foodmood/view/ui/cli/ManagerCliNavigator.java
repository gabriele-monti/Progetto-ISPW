// package it.foodmood.view.ui.cli;

// import it.foodmood.view.ui.UiFactory;
// import it.foodmood.view.ui.cli.customer.CliCustomerMenuView;

// public class ManagerCliNavigator implements CliNavigator {

//     private final UiFactory factory;

//     public ManagerCliNavigator(UiFactory factory){
//         this.factory = factory;
//     }

//     @Override
//     public void start(){
//         ManagerCliNavigator menuView = new ManagerCliNavigator();

//         boolean exit = false;
//         while(!exit){
//             CliPages page = menuView.show();

//             switch (page) {
//                 case LOGIN ->  factory.showLoginView();
//                 case REGISTRATION -> factory.showRegistrationView();
//                 case LOGOUT -> exit = true;
//             }
//         }
//     }
// }
