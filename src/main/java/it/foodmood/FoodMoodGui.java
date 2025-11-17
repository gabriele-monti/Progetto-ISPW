package it.foodmood;

import it.foodmood.config.ApplicationEnvironment;
import it.foodmood.config.UserMode;
import it.foodmood.view.ui.LoginView;
import it.foodmood.view.ui.UiFactory;
import javafx.application.Application;
import javafx.stage.Stage;

public class FoodMoodGui extends Application {

    private static ApplicationEnvironment environment;

    public static void setEnvironment(ApplicationEnvironment env){
        environment = env;
    }

    @Override
    public void start(Stage stage){

        UserMode userMode = environment.config().getUserMode();

        UiFactory.initGui(stage, userMode);

        LoginView loginView = UiFactory.getInstance().createLoginView();
        loginView.show();
    }
}
