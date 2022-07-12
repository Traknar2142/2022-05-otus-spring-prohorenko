package ru.otus.homework.service;

import ru.otus.homework.domain.Book;
import ru.otus.homework.exceptions.EntityNotFoundException;

/**
 * @author Прохоренко Виктор
 */
public interface BookService {
    void printAllBooks();
    void printBookById(Long id);
    Book addBook(Book book);
    Book getById(Long id);
    Book updateBook(Book book);
    void deleteBook(Long id);
}
