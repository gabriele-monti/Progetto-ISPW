package it.foodmood.infrastructure.bootstrap;

public final class BootstrapFactory {

    public ApplicationBootstrap create(UiMode mode){
        return switch(mode){
            case CLI -> new CLIBootstrap();
            case GUI -> new GUIBootstrap();
        };
    }
}
