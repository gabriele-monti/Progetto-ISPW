package it.foodmood.infrastructure.bootstrap;

import it.foodmood.ApplicationEnvironment;

public class CLIBootstrap implements ApplicationBootstrap{

    @Override
    public void start(ApplicationEnvironment environment){
        System.out.println("Avvio software in modalità: 'Command Line'");
    }
}
