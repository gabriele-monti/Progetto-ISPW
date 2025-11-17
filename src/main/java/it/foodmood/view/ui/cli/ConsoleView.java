package it.foodmood.view.ui.cli;

import java.util.Objects;

import it.foodmood.infrastructure.io.InputReader;
import it.foodmood.infrastructure.io.OutputWriter;
import it.foodmood.infrastructure.io.console.ConsoleInputReader;
import it.foodmood.infrastructure.io.console.ConsoleOutputWriter;
import it.foodmood.view.ui.theme.UiTheme;

public abstract class ConsoleView implements CliUserInterface{

    protected final InputReader in;
    protected final OutputWriter out;
    protected final UiTheme theme;

    private static final String CLEAR_CONSOLE = "\033[H\033[J";

    protected ConsoleView(InputReader in, OutputWriter out, UiTheme theme){
        this.in = ConsoleInputReader.getInstance();
        this.out = new ConsoleOutputWriter();
        this.theme = Objects.requireNonNull(theme);
    }

    @Override
    public void showError(String error){
        out.println(theme.error(error));
    }

    @Override
    public void showSuccess(String success){
        out.println(theme.success(success));
    }

    @Override
    public void showWarning(String warning){
        out.println(theme.warning(warning));
    }

    @Override
    public void showInfo(String info){
        out.println(theme.info(info));
    }

    @Override
    public void showSeparator(String separator){
        out.println(separator);
    }
    
    @Override
    public void showTitle(String title){
        out.displayTitle(title);
    }

    @Override
    public void waitForEnter(String prompt){
        String message = (prompt == null || prompt.isBlank()) ? "Premi INVIO per continuare" : prompt;

        out.print(theme.info(message));
        in.readLine();
    }

    @Override
    public void clearScreen(){
        out.print(CLEAR_CONSOLE);
    }

    protected String askInput(String prompt){
        while(true){
            out.print(prompt);
            String input = in.readLine();

            if(input == null || input.isBlank()){
                showError("Il campo non può essere vuoto");
                continue;
            }
            return input.trim();
        }
    }
}
