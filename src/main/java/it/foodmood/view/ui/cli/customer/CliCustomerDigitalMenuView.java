package it.foodmood.view.ui.cli.customer;

import java.util.List;

import it.foodmood.bean.DishBean;
import it.foodmood.domain.value.CourseType;
import it.foodmood.exception.CartException;
import it.foodmood.exception.DishException;
import it.foodmood.view.boundary.CartBoundary;
import it.foodmood.view.boundary.DishBoundary;

import it.foodmood.view.ui.cli.ProtectedConsoleView;

public class CliCustomerDigitalMenuView extends ProtectedConsoleView{

    private final DishBoundary dishBoundary;
    private final CartBoundary cartBoundary;
    
    public CliCustomerDigitalMenuView(DishBoundary dishBoundary, CartBoundary cartBoundary){
        super();
        this.dishBoundary = dishBoundary;
        this.cartBoundary = cartBoundary;
    }

    public void displayPage(){
        boolean back = false;
        while(!back){
            clearScreen();
            showTitle("Menù Digitale");

            showInfo("1. Antipasti");
            showInfo("2. Primi");
            showInfo("3. Secondi");
            showInfo("4. Pizza");
            showInfo("5. Contorni");
            showInfo("6. Dolci");
            showInfo("7. Frutta");
            showInfo("8. Bevande");
            showInfo("0. Torna al menù principale");

            String choice = askInput("\nSeleziona un'opzione: ");

            try {
                switch(choice){
                    case "1" -> proposeDishesByCourseType(CourseType.APPETIZER, "Antipasti");
                    case "2" -> proposeDishesByCourseType(CourseType.FIRST_COURSE, "Primi");
                    case "3" -> proposeDishesByCourseType(CourseType.MAIN_COURSE, "Secondi");
                    case "4" -> proposeDishesByCourseType(CourseType.PIZZA, "Pizze");
                    case "5" -> proposeDishesByCourseType(CourseType.SIDE_DISH, "Contorni");
                    case "6" -> proposeDishesByCourseType(CourseType.DESSERT, "Dolci");
                    case "7" -> proposeDishesByCourseType(CourseType.FRUIT, "Frutta");
                    case "8" -> proposeDishesByCourseType(CourseType.BEVERAGE, "Bevande");
                    case "0" -> back = true;
                    default  -> showError("Scelta non valida, riprova.");
                }
            } catch (DishException e) {
                showError(e.getMessage());
                waitForEnter(null);
            }
        }
    }

    private void proposeDishesByCourseType(CourseType courseType, String title) throws DishException{
        clearScreen();
        showTitle(title);

        List<DishBean> dishes = dishBoundary.getDishesByCourseType(courseType);

        if(dishes.isEmpty()){
            showWarning("Nessun piatto disponibile");
            waitForEnter(null);
            return;
        } 

        showDishTable(dishes);

        if(!askConfirmation("Vuoi aggiungere un articolo all'ordine")){
            return;
        }

        addItems(dishes);
    }

    private void addItems(List<DishBean> dishes){
        while(true){
            String input = askInputOrBack("Inserisci il numero dell'articolo");
            Integer index = parseInteger(input, dishes.size());
            if(index == null ){
                showWarning("Input non valido");
                continue;
            }

            int quantity = askPositiveInt("Quantità: ");

            DishBean selected = dishes.get(index - 1);

            try {
                cartBoundary.addProduct(selected.getId(), quantity);
                showSuccess("Articolo '" + selected.getName() + "' aggiunto con successo.");

            } catch (CartException e) {
                showWarning(e.getMessage());
            }

            if(!askConfirmation("Vuoi aggiungere un altro articolo?")){
                return;
            }
        }
    }
}
