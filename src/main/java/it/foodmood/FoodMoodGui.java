package it.foodmood;

import it.foodmood.config.ApplicationEnvironment;
import it.foodmood.config.UserMode;
import it.foodmood.view.boundary.LoginBoundary;
import it.foodmood.view.ui.gui.GuiLoginView;
import it.foodmood.view.ui.gui.GuiNavigator;
import it.foodmood.view.ui.gui.GuiPages;
import javafx.application.Application;
import javafx.stage.Stage;

public class FoodMoodGui extends Application {

    private static ApplicationEnvironment environment;

    public static void setEnvironment(ApplicationEnvironment env){
        environment = env;
    }

    @Override
    public void start(Stage stage){
        GuiNavigator navigator = new GuiNavigator(stage);

        GuiLoginView controller = navigator.goTo(GuiPages.LOGIN);

        UserMode userMode = environment.config().getUserMode();

        LoginBoundary boundary = new LoginBoundary(userMode);

        controller.setBoundary(boundary);
    }
}
