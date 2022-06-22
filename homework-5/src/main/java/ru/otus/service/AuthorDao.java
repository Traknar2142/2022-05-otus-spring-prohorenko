package ru.otus.service;

import ru.otus.domain.Author;

/**
 * @author Прохоренко Виктор
 */
public interface AuthorDao {
    Author saveAuthor(Author author);
    Author getAuthorById(Long id);
    Author updateAuthor(Author author);
    void deleteAuthor(Author author);
}
