package ru.otus.homework.dao;

import ru.otus.homework.domain.Genre;

/**
 * @author Прохоренко Виктор
 */
public interface GenreDao {
    Genre saveGenre(Genre genre);
    Genre getGenreById(Long id);
    Genre getGenreByName(String name);
    Genre updateGenre(Genre genre);
    void deleteGenre(Genre genre);
}
