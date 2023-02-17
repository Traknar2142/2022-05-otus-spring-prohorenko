package ru.otus.homework.service;

import ru.otus.homework.domain.Book;

import java.util.List;

/**
 * @author Прохоренко Виктор
 */
public interface BookRenderService {
    void printFormatMessage(List<Book> list);
}
