package ru.otus.service;

import ru.otus.domain.Book;

/**
 * @author Прохоренко Виктор
 */
public interface BookDao {
    Book saveBook(Book book);
    Book getBookById(Long id);
    Book updateBook(Book book);
    void deleteBook(Book book);
}
