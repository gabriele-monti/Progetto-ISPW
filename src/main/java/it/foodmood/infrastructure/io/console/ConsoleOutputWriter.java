package it.foodmood.infrastructure.io.console;

import it.foodmood.infrastructure.io.OutputWriter;

public final class ConsoleOutputWriter implements OutputWriter {
    @Override
    public void print(String s){
        System.out.print(s);
    }

    @Override
    public void println(String s){
        System.out.println(s);
    }
}
