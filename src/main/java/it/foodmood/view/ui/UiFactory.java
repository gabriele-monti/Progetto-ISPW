package it.foodmood.view.ui;

import it.foodmood.config.UserMode;
import it.foodmood.infrastructure.bootstrap.UiMode;
import it.foodmood.view.ui.cli.CliFactory;
import it.foodmood.view.ui.gui.GuiFactory;


public abstract class UiFactory {
    private static UiFactory instance;

    // protected UiFactory() {}

    public abstract LoginView createLoginView();

        public static synchronized void init(UiMode uiMode, UserMode userMode){
        if(instance != null) return; 
        instance = switch(uiMode){
            case CLI -> new CliFactory(userMode);
            case GUI -> new GuiFactory(userMode);
        };
    }

    public static synchronized UiFactory getInstance(){
        if(instance == null){
            throw new IllegalStateException("UiFactory non inizializzata. Chiama init(uiMode) prima.");
        }
        return instance;
    }
}
