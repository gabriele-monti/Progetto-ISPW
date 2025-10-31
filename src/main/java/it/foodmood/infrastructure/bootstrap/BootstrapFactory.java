package it.foodmood.infrastructure.bootstrap;

import java.util.Objects;

import it.foodmood.infrastructure.io.OutputWriter;

public final class BootstrapFactory {

    private final OutputWriter out;

    public BootstrapFactory(OutputWriter out){
        this.out = Objects.requireNonNull(out, "out");
    }

    public ApplicationBootstrap create(UiMode mode){
        return switch(mode){
            case CLI -> new CLIBootstrap(out);
            case GUI -> new GUIBootstrap(out);
        };
    }
}
