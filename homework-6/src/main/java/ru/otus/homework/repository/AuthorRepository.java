package ru.otus.homework.repository;

import ru.otus.homework.domain.Author;

import java.util.Optional;

/**
 * @author Прохоренко Виктор
 */
public interface AuthorRepository {
    Author saveAuthor(Author author);
    Optional<Author> getAuthorById(Long id);
    Optional<Author> getAuthorByName(String name);
    Author updateAuthor(Author author);
    void deleteAuthor(Author author);
}
