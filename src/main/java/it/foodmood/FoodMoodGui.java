package it.foodmood;

import it.foodmood.config.ApplicationEnvironment;
import it.foodmood.config.UserMode;
import it.foodmood.view.ui.UiFactory;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class FoodMoodGui extends Application {

    private static ApplicationEnvironment environment;

    public static void setEnvironment(ApplicationEnvironment env){
        environment = env;
    }

    @Override
    public void start(Stage stage){

        UserMode userMode = environment.config().getUserMode();

        Scene scene = new Scene(new StackPane());

        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();

        UiFactory.initGui(scene, userMode);

        UiFactory.getInstance().createLoginView();

        // LoginView loginView = UiFactory.getInstance().createLoginView();
        // loginView.show();
    }

    @Override
    public void stop(){
        System.out.println("Grazie per aver utilizzato FoodMood, a presto!\n");
        System.exit(0);
    }
}
