package it.foodmood.view.ui.cli.customer;

import java.util.List;

import it.foodmood.bean.CartItemBean;
import it.foodmood.bean.DishBean;
import it.foodmood.controller.CartController;
import it.foodmood.controller.MenuController;
import it.foodmood.domain.value.CourseType;
import it.foodmood.exception.CartException;
import it.foodmood.exception.DishException;
import it.foodmood.view.ui.cli.TableConsoleView;

public class CliCustomerDigitalMenuView extends TableConsoleView{
    
    public CliCustomerDigitalMenuView(){
        super();
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
                    case "1" -> showDishesByCourseType(CourseType.APPETIZER, "Antipasti");
                    case "2" -> showDishesByCourseType(CourseType.FIRST_COURSE, "Primi");
                    case "3" -> showDishesByCourseType(CourseType.MAIN_COURSE, "Secondi");
                    case "4" -> showDishesByCourseType(CourseType.PIZZA, "Pizze");
                    case "5" -> showDishesByCourseType(CourseType.SIDE_DISH, "Contorni");
                    case "6" -> showDishesByCourseType(CourseType.DESSERT, "Dolci");
                    case "7" -> showDishesByCourseType(CourseType.FRUIT, "Frutta");
                    case "8" -> showDishesByCourseType(CourseType.BEVERAGE, "Bevande");
                    case "0" -> back = true;
                    default  -> showError("Scelta non valida, riprova.");
                }
            } catch (DishException e) {
                showError(e.getMessage());
                waitForEnter(null);
            }
        }
    }

    private void showDishesByCourseType(CourseType courseType, String title) throws DishException{
        clearScreen();
        showTitle(title);
        
        MenuController menuController = new MenuController();
        List<DishBean> dishes = menuController.loadDishesByCourseType(courseType);

        if(dishes.isEmpty()){
            showWarning("Nessun piatto disponibile");
            waitForEnter(null);
            return;
        } 

        showDishTable(dishes);

        if(!askConfirmation("Vuoi aggiungere un articolo all'ordine")){
            return;
        }

        addItemsToCart(dishes);
    }

    private void addItemsToCart(List<DishBean> dishes){
        CartController cartController = new CartController();

        while(true){
            String input = askInputOrBack("Inserisci il numero dell'articolo");
            Integer index = parseInteger(input, dishes.size());
            if(index == null ){
                showWarning("Input non valido");
                continue;
            }

            int quantity = askPositiveInt("Quantità: ");

            DishBean selected = dishes.get(index - 1);
            CartItemBean itemBean = new CartItemBean();
            itemBean.setDishId(selected.getId());
            itemBean.setQuantity(quantity);

            try {
                cartController.addToCart(itemBean);
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
