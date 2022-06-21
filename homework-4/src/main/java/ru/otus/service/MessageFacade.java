package ru.otus.service;

/**
 * @author Прохоренко Виктор
 */
public interface MessageFacade{
    String inputMessage();
    String outputMessage(String message);
    String outputLocalizedMessage(String message);
    String getLocalizationMessage(String message);
}
