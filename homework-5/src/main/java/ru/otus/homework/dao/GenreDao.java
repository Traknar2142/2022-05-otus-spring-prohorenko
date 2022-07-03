package ru.otus.homework.dao;

import ru.otus.homework.domain.Genre;
import ru.otus.homework.exceptions.EntityNotFoundInDbException;

/**
 * @author Прохоренко Виктор
 */
public interface GenreDao {
    Genre saveGenre(Genre genre) throws EntityNotFoundInDbException;
    Genre getGenreById(Long id) throws EntityNotFoundInDbException;
    Genre getGenreByName(String name) throws EntityNotFoundInDbException;
    Genre updateGenre(Genre genre) throws EntityNotFoundInDbException;
    void deleteGenre(Genre genre);
}
