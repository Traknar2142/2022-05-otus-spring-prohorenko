package ru.otus.homework.dao;

import ru.otus.homework.domain.Author;

/**
 * @author Прохоренко Виктор
 */
public interface AuthorDao {
    Author saveAuthor(Author author);
    Author getAuthorById(Long id);
    Author getAuthorByName(String name);
    Author updateAuthor(Author author);
    void deleteAuthor(Author author);
}
