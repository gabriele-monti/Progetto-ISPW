package it.foodmood.infrastructure.bootstrap;

import it.foodmood.config.ApplicationEnvironment;
import it.foodmood.infrastructure.io.OutputWriter;
import it.foodmood.infrastructure.io.console.ConsoleOutputWriter;
import it.foodmood.view.ui.UiFactory;
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

        UiFactory.initCli(mode);
        UiFactory cliFactory = UiFactory.getInstance();
        CliNavigator navigator = CliNavigatorFactory.create(mode, cliFactory);

        navigator.start();
    }
}
