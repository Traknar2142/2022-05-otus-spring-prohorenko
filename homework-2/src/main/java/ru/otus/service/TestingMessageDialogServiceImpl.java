package ru.otus.service;

import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Scanner;

/**
 * @author Прохоренко Виктор
 */
public class TestingMessageDialogServiceImpl implements TestingMessageDialogService{
    private final Scanner scanner;

    public TestingMessageDialogServiceImpl(InputStream in) {
        this.scanner = new Scanner(in);
    }

    @Override
    public String inputMessage() {
        return scanner.next();

    }

    @Override
    public String outputMessage(String message) {
        System.out.println(message);
        return message;
    }
}
