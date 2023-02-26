package ru.otus.homework.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.homework.domain.Genre;

import java.util.Optional;

/**
 * @author Прохоренко Виктор
 */
public interface GenreRepository extends CrudRepository<Genre, Long> {
    Optional<Genre> findByName(String name);
}
