package ru.otus.homework.service;

import ru.otus.homework.domain.Book;

/**
 * @author Прохоренко Виктор
 */
public interface EntityProcessor<S> {
    S addProcess();
    S updateProcess();
}
