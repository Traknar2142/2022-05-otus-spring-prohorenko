package ru.otus.homework.service;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * @author Прохоренко Виктор
 */
public class MessageDialogServiceImpl implements MessageDialogService{
    private final Scanner scanner;
    private final PrintStream output;

    public MessageDialogServiceImpl(InputStream in, PrintStream output) {
        this.scanner = new Scanner(in);
        this.output = output;
    }

    @Override
    public String inputMessage() {
        return scanner.nextLine();

    }

    @Override
    public String outputMessage(String message) {
        output.println(message);
        return message;
    }
}
