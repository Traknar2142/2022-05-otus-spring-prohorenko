package ru.otus.service;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * @author Прохоренко Виктор
 */
public class TestingMessageDialogServiceImpl implements MessageDialogService {
    private final Scanner scanner;
    private final PrintStream output;

    public TestingMessageDialogServiceImpl(InputStream in, PrintStream output) {
        this.scanner = new Scanner(in);
        this.output = output;
    }

    @Override
    public String inputMessage() {
        return scanner.next();

    }

    @Override
    public String outputMessage(String message) {
        output.println(message);
        return message;
    }
}
