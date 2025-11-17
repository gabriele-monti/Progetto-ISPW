package it.foodmood.view.ui;

import it.foodmood.config.UserMode;
import it.foodmood.view.ui.cli.CliFactory;
import it.foodmood.view.ui.gui.GuiFactory;
import javafx.stage.Stage;

public abstract class UiFactory {
    private static UiFactory instance;

    public abstract LoginView createLoginView();
    public abstract RegistrationView createRegistrationView();

    public static synchronized void initCli(UserMode userMode){
        if(instance != null) return; 
        instance = new CliFactory(userMode);
    }

    public static synchronized void initGui(Stage stage, UserMode userMode){
        if(instance != null) return; 
        instance = new GuiFactory(stage, userMode);
    }

    public static synchronized UiFactory getInstance(){
        if(instance == null){
            throw new IllegalStateException("UiFactory non inizializzata.");
        }
        return instance;
    }
}
