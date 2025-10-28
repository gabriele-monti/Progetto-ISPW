package it.foodmood.infrastructure.bootstrap;

public final class BootstrapFactory {
    private BootstrapFactory() {}

    public static ApplicationBootstrap create(UiMode mode){
        return switch(mode){
            case CLI -> new CLIBootstrap();
            case GUI -> new GUIBootstrap();
        };
    }
}
