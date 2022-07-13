package ru.otus.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework.domain.Genre;

import java.util.Optional;

/**
 * @author Прохоренко Виктор
 */
public interface GenreRepository extends JpaRepository<Genre, Long> {
    Optional<Genre> findByName(String name);
}
