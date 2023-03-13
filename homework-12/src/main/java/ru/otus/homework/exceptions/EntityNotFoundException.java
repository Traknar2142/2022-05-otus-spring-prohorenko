package ru.otus.homework.exceptions;

/**
 * @author Прохоренко Виктор
 */
public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(String message) {
        super(message);
    }
}
