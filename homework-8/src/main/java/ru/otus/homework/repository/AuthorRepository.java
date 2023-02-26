package ru.otus.homework.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.homework.domain.Author;

import java.util.Optional;

/**
 * @author Прохоренко Виктор
 */
public interface AuthorRepository extends CrudRepository<Author, String> {
    Optional<Author> findByName(String name);
}
