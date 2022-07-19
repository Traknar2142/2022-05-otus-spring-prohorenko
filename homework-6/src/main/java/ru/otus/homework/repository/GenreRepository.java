package ru.otus.homework.repository;

import ru.otus.homework.domain.Genre;

import java.util.Optional;

/**
 * @author Прохоренко Виктор
 */
public interface GenreRepository {
    Genre saveGenre(Genre genre);
    Optional<Genre> getGenreById(Long id);
    Optional<Genre> getGenreByName(String name);
    Genre updateGenre(Genre genre);
    void deleteGenre(Genre genre);
}
