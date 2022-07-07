package ru.otus.homework.dao;

import ru.otus.homework.domain.Book;

import java.util.List;

/**
 * @author Прохоренко Виктор
 */
public interface BookDao {
    Book saveBook(Book book);
    Book getBookById(Long id);
    List<Book> getAll();
    Book updateBook(Book book);
    void deleteBookById(Long id);
}
