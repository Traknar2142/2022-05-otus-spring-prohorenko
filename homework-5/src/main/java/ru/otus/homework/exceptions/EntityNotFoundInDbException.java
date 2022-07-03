package ru.otus.homework.exceptions;

/**
 * @author Прохоренко Виктор
 */
public class EntityNotFoundInDbException extends Exception{
    public EntityNotFoundInDbException(String message) {
        super(message);
    }
}
