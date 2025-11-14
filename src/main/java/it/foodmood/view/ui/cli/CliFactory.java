package it.foodmood.view.ui.cli;

import it.foodmood.config.UserMode;
import it.foodmood.infrastructure.io.InputReader;
import it.foodmood.infrastructure.io.OutputWriter;
import it.foodmood.infrastructure.io.console.ConsoleInputReader;
import it.foodmood.infrastructure.io.console.ConsoleOutputWriter;
import it.foodmood.view.boundary.LoginBoundary;
import it.foodmood.view.boundary.RegistrationBoundary;
import it.foodmood.view.ui.LoginView;
import it.foodmood.view.ui.RegistrationView;
import it.foodmood.view.ui.UiFactory;
import it.foodmood.view.ui.theme.AnsiUiTheme;
import it.foodmood.view.ui.theme.UiTheme;

public final class CliFactory extends UiFactory{

    private final InputReader in;
    private final OutputWriter out;
    private final UiTheme theme;
    private final UserMode userMode;

    public CliFactory(UserMode userMode){
        this.in = ConsoleInputReader.getInstance();
        this.out = new ConsoleOutputWriter();
        this.theme = new AnsiUiTheme();
        this.userMode = userMode;
    }
    
    @Override
    public LoginView createLoginView(){
        LoginBoundary boundary = new LoginBoundary(userMode);
        return new CliLoginView(in, out, theme, boundary);
    }  

    @Override
    public RegistrationView createRegistrationView(){
        RegistrationBoundary boundary = new RegistrationBoundary();
        return new CliRegistrationView(in, out, theme, boundary);
    }  
}