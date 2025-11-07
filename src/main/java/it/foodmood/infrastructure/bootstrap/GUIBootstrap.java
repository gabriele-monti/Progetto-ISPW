package it.foodmood.infrastructure.bootstrap;

import java.util.Objects;

import it.foodmood.config.ApplicationEnvironment;
import it.foodmood.infrastructure.io.OutputWriter;

public class GUIBootstrap implements ApplicationBootstrap {

    private final OutputWriter out;

    public GUIBootstrap(OutputWriter out){
        this.out = Objects.requireNonNull(out);
    }

    @Override
    public void start(ApplicationEnvironment environment){
        out.println("Avvio software con interfaccia grafica: 'JavaFX'");
    }
}
