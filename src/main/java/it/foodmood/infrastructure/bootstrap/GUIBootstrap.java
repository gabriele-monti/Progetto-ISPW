package it.foodmood.infrastructure.bootstrap;

import it.foodmood.ApplicationEnvironment;

public class GUIBootstrap implements ApplicationBootstrap {

    @Override
    public void start(ApplicationEnvironment environment){
        System.out.println("Avvio software con interfaccia grafica: 'JavaFX'");
    }
}
