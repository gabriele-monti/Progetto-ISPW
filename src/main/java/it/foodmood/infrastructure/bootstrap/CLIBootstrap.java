package it.foodmood.infrastructure.bootstrap;

import it.foodmood.config.ApplicationEnvironment;
import it.foodmood.infrastructure.io.OutputWriter;
import it.foodmood.infrastructure.io.console.ConsoleOutputWriter;
import it.foodmood.view.ui.cli.CliFactory;
import it.foodmood.view.ui.cli.CliNavigator;
import it.foodmood.view.ui.cli.CliNavigatorFactory;

public class CliBootstrap implements ApplicationBootstrap{

    private final OutputWriter out;

    public CliBootstrap(){
        this.out = new ConsoleOutputWriter();
    }
    
    @Override
    public void start(ApplicationEnvironment environment){
        out.println("Avvio software in modalità: command line\n");

        var mode = environment.config().getUserMode();

        CliFactory factory = new CliFactory(mode);
        CliNavigator navigator = CliNavigatorFactory.create(mode, factory);

        navigator.start();

        stop();
    }

    private void stop(){
        System.out.print("\033[H\033[J");
        System.out.println("Grazie per aver utilizzato FoodMood, a presto!\n");
    }
}
