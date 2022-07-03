package ru.otus.homework.dao;

import ru.otus.homework.domain.Author;
import ru.otus.homework.exceptions.EntityNotFoundInDbException;

/**
 * @author Прохоренко Виктор
 */
public interface AuthorDao {
    Author saveAuthor(Author author) throws EntityNotFoundInDbException;
    Author getAuthorById(Long id) throws EntityNotFoundInDbException;
    Author getAuthorByName(String name) throws EntityNotFoundInDbException;
    Author updateAuthor(Author author) throws EntityNotFoundInDbException;
    void deleteAuthor(Author author);
}
