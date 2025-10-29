package it.foodmood.ui.theme;

public final class AnsiUitheme implements UiTheme {

    private static final String RESET = "\u001B[0m";
    private static final String ERROR = "\u001B[31m";
    private static final String WARNING = "\u001B[33m";
    private static final String SUCCESS = "\u001B[32m";
    private static final String INFO = "\u001B[37m";
    private static final String BOLD = "\u001B[1m";


    @Override
    public String primary(String theme){
        return theme;
    }

    @Override
    public String error(String text){
        return ERROR + text  + RESET;
    }

    @Override
    public String warning(String text){
        return WARNING + text  + RESET;
    }

    @Override
    public String success(String text){
        return SUCCESS + text  + RESET;
    }

    @Override
    public String info(String text){
        return INFO + text  + RESET;
    }

    @Override
    public String bold(String text){
        return BOLD + text  + RESET;
    }
}
