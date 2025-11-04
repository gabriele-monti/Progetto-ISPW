package it.foodmood.ui.cli;

import java.util.Objects;

import it.foodmood.infrastructure.io.InputReader;
import it.foodmood.infrastructure.io.OutputWriter;
import it.foodmood.ui.core.UserInterface;
import it.foodmood.ui.theme.UiTheme;

public final class ConsoleView implements UserInterface{
    private final InputReader in;
    private final OutputWriter out;
    private final UiTheme theme;

    public ConsoleView(InputReader in, OutputWriter out, UiTheme theme){
        this.in = Objects.requireNonNull(in);
        this.out = Objects.requireNonNull(out);
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
        out.println(theme.bold(title));
    }

   @Override
   public void waitForEnter(String prompt){
        String message = (prompt == null || prompt.isBlank()) ? "Premi INVIO per continuare" : prompt;

        out.print(theme.info(message));
        in.readLine();
   }

   @Override
   public void clearScreen(){
       final String CLEAR_CONSOLE = "\033[H\033[J";
       out.print(CLEAR_CONSOLE);
   }
}
