package ru.otus.homework.dao;

import ru.otus.homework.domain.Book;
import ru.otus.homework.exceptions.EntityNotFoundInDbException;

import java.util.List;

/**
 * @author Прохоренко Виктор
 */
public interface BookDao {
    Book saveBook(Book book) throws EntityNotFoundInDbException;
    Book getBookById(Long id) throws EntityNotFoundInDbException;
    List<Book> getAll();
    Book updateBook(Book book) throws EntityNotFoundInDbException;
    void deleteBookById(Long id) throws EntityNotFoundInDbException;
}
