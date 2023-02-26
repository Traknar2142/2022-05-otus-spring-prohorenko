package ru.otus.homework.service;

import ru.otus.homework.domain.Book;

/**
 * @author Прохоренко Виктор
 */
public interface BookService {
    void printAllBooks();
    void printBookById(String id);
    Book addBook(Book book);
    Book getById(String id);
    Book updateBook(Book book);
    void deleteBook(String id);
}
