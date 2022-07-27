package ru.otus.homework.service;

import ru.otus.homework.domain.Book;

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
