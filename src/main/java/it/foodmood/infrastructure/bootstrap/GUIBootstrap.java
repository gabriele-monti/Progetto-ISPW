package it.foodmood.infrastructure.bootstrap;

import it.foodmood.FoodMoodGui;
import it.foodmood.config.ApplicationEnvironment;
import it.foodmood.infrastructure.io.OutputWriter;
import it.foodmood.infrastructure.io.console.ConsoleOutputWriter;
import javafx.application.Application;

public class GUIBootstrap implements ApplicationBootstrap {

    OutputWriter out = new ConsoleOutputWriter();

    @Override
    public void start(ApplicationEnvironment environment){
        out.println("Avvio software con interfaccia grafica: 'JavaFX'");

        FoodMoodGui.setEnvironment(environment);

        Application.launch(FoodMoodGui.class);
    }
}
