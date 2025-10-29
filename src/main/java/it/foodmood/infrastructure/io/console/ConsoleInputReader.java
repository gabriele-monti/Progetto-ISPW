package it.foodmood.infrastructure.io.console;

import java.util.Objects;
import java.util.Scanner;

import it.foodmood.infrastructure.io.InputReader;

public final class ConsoleInputReader implements InputReader {
    private final Scanner scanner;
    private boolean closed;

    public ConsoleInputReader(){
        this(new Scanner(System.in));
    }

    public ConsoleInputReader(Scanner scanner){
        this.scanner = Objects.requireNonNull(scanner, "scanner");
    }

    @Override
    public String readLine(){
        if(closed){
            throw new IllegalStateException("Input da tastiera chiuso.");
        }
        return scanner.nextLine();
    }

    @Override
    public void close(){
        closed = true;
    }
}
