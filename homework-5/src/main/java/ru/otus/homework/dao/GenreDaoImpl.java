package ru.otus.homework.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.exceptions.EntityNotFoundException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Objects;

/**
 * @author Прохоренко Виктор
 */
@Repository
public class GenreDaoImpl implements GenreDao {
    private final NamedParameterJdbcOperations jdbcTemplate;

    public GenreDaoImpl(NamedParameterJdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Genre saveGenre(Genre genre) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("name", genre.getName());
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update("INSERT INTO t_genre(name) values (:name)",
                parameters, keyHolder, new String[]{"id"});
        return getGenreById(Objects.requireNonNull(keyHolder.getKey()).longValue());
    }

    @Override
    public Genre getGenreById(Long id) {
        try {
            return jdbcTemplate.queryForObject("SELECT id, name FROM t_genre WHERE id = :id",
                    Map.of("id", id), new GenreDaoImpl.GenreMapper());

        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException(MessageFormat.format("Запись о жанре c id {0} не найдена", id));
        }
    }

    @Override
    public Genre getGenreByName(String name) {
        try {
            return jdbcTemplate.queryForObject("SELECT id, name FROM t_genre WHERE name = :name",
                    Map.of("name", name), new GenreDaoImpl.GenreMapper());
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException(MessageFormat.format("Запись о жанре c именем {0} не найдена", name));
        }
    }

    @Override
    public Genre updateGenre(Genre genre)  {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", genre.getId());
        parameters.addValue("name", genre.getName());
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update("UPDATE t_genre SET name = :name WHERE id = :id",
                parameters, keyHolder, new String[]{"id"});
        return getGenreById(Objects.requireNonNull(keyHolder.getKey()).longValue());
    }

    @Override
    public void deleteGenre(Genre genre) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", genre.getId());
        jdbcTemplate.update("DELETE FROM t_genre WHERE id = :id", parameters);
    }

    private static class GenreMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Genre(rs.getLong("id"), rs.getString("name"));
        }
    }
}
