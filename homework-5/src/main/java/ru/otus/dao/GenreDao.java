package ru.otus.dao;

import ru.otus.domain.Genre;

/**
 * @author Прохоренко Виктор
 */
public interface GenreDao {
    Genre saveGenre(Genre genre);
    Genre getGenreById(Long id);
    Genre updateGenre(Genre genre);
    void deleteGenre(Genre genre);
}
