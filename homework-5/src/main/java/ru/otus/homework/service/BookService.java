package ru.otus.homework.service;

import ru.otus.homework.domain.Book;
import ru.otus.homework.exceptions.EntityNotFoundInDbException;

/**
 * @author Прохоренко Виктор
 */
public interface BookService {
    void printAllBooks();
    Book addBook(Book book) throws EntityNotFoundInDbException;
    Book getById(Long id) throws EntityNotFoundInDbException;
    Book updateBook(Book book) throws EntityNotFoundInDbException;
    void deleteBook(Long id) throws EntityNotFoundInDbException;
}
