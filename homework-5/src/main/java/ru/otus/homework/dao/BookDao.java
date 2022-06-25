package ru.otus.homework.dao;

import ru.otus.homework.domain.Book;

/**
 * @author Прохоренко Виктор
 */
public interface BookDao {
    Book saveBook(Book book);
    Book getBookById(Long id);
    Book updateBook(Book book);
    void deleteBook(Book book);
}
