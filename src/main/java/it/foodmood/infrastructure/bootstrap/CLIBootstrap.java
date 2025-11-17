package it.foodmood.infrastructure.bootstrap;


import it.foodmood.config.ApplicationEnvironment;
import it.foodmood.infrastructure.io.InputReader;
import it.foodmood.infrastructure.io.OutputWriter;
import it.foodmood.infrastructure.io.console.ConsoleInputReader;
import it.foodmood.infrastructure.io.console.ConsoleOutputWriter;
import it.foodmood.view.ui.LoginView;
import it.foodmood.view.ui.RegistrationView;
import it.foodmood.view.ui.UiFactory;

public class CLIBootstrap implements ApplicationBootstrap{

    private final InputReader in;
    private final OutputWriter out;

    public CLIBootstrap(){
        this.in = ConsoleInputReader.getInstance();
        this.out = new ConsoleOutputWriter();
    }
    
    @Override
    public void start(ApplicationEnvironment environment){
        out.println("Avvio software in modalità: command line\n");

        UiFactory.initCli(environment.config().getUserMode());

        RegistrationView registrationView = UiFactory.getInstance().createRegistrationView();
        LoginView loginView = UiFactory.getInstance().createLoginView();

        out.print("Hai già un account? (s/n): ");
        String response = in.readLine();
        if("n".equalsIgnoreCase(response.trim())){
            registrationView.show();
        }
        loginView.show();
    }
}
