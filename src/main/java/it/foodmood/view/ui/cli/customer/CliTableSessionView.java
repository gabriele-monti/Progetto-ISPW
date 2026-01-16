package it.foodmood.view.ui.cli.customer;

import it.foodmood.bean.TableSessionBean;
import it.foodmood.exception.TableException;
import it.foodmood.exception.TableSessionException;
import it.foodmood.view.boundary.TableSessionBoundary;
import it.foodmood.view.ui.cli.ConsoleView;

public class CliTableSessionView extends ConsoleView {

    private static final String TITLE = "TAVOLO";

    private final TableSessionBoundary boundary;

    public CliTableSessionView(TableSessionBoundary boundary){
        super();
        this.boundary = boundary;
    }

    public TableSessionBean displayPage(){
        clearScreen();
        while(true){
            showTitle(TITLE);
            try {
                int tableNumber = askPositiveInt("Inserisci il numero del tuo tavolo: ");
                return boundary.enterSession(tableNumber);
                
            } catch (TableException e) {
                clearScreen();
                showError(e.getMessage());
                showInfo("Riprova\n");
            } catch (TableSessionException e){
                showError(e.getMessage());
            }
        }
    }
}
