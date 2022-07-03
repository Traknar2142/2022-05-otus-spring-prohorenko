package ru.otus.homework.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.exceptions.EntityNotFoundInDbException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class BookDaoImpl implements BookDao {
    private final NamedParameterJdbcOperations jdbcTemplate;

    public BookDaoImpl(NamedParameterJdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Book saveBook(Book book) throws EntityNotFoundInDbException {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("title", book.getTitle());
        parameters.addValue("author_id", book.getAuthor() != null ? book.getAuthor().getId() : null);
        parameters.addValue("genre_id", book.getGenre() != null ? book.getGenre().getId() : null);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update("INSERT INTO t_book(title, author_id, genre_id) values (:title, :author_id, :genre_id)",
                parameters, keyHolder, new String[]{"id"});
        return getBookById(Objects.requireNonNull(keyHolder.getKey()).longValue());
    }

    @Override
    public Book getBookById(Long id) throws EntityNotFoundInDbException {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT t_book.id, t_book.title, t_book.genre_id, t_genre.name as genre_name, t_book.author_id, t_author.name as author_name\n" +
                            "FROM t_book\n" +
                            "LEFT JOIN t_author on t_book.author_id = t_author.id\n" +
                            "LEFT JOIN t_genre on t_book.genre_id = t_genre.id WHERE t_book.id = :id",
                    Map.of("id", id), new BookMapper());
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundInDbException(MessageFormat.format("Запись о книге c id {0} не найдена", id));
        }
    }

    @Override
    public List<Book> getAll() {
        return jdbcTemplate.query(
                "SELECT t_book.id, t_book.title, t_book.genre_id, t_genre.name as genre_name, t_book.author_id, t_author.name as author_name\n" +
                        "FROM t_book \n" +
                        "LEFT JOIN t_author on t_book.author_id = t_author.id\n" +
                        "LEFT JOIN t_genre on t_book.genre_id = t_genre.id", new BookMapper());
    }

    @Override
    public Book updateBook(Book book) throws EntityNotFoundInDbException {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", book.getId());
        parameters.addValue("title", book.getTitle());
        parameters.addValue("author_id", book.getAuthor().getId());
        parameters.addValue("genre_id", book.getGenre().getId());
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update("UPDATE t_book SET title = :title, author_id = :author_id, genre_id = :genre_id WHERE id = :id",
                parameters, keyHolder, new String[]{"id"});
        return getBookById(Objects.requireNonNull(keyHolder.getKey()).longValue());
    }

    @Override
    public void deleteBookById(Long id) throws EntityNotFoundInDbException {
        try {
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("id", id);
            jdbcTemplate.update("DELETE FROM t_book WHERE id = :id", parameters);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundInDbException(MessageFormat.format("Запись о книге c id {0} не найдена", id));
        }
    }

    private static class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Book(rs.getLong("id"),
                    rs.getString("title"),
                    new Author(rs.getLong("author_id"), rs.getString("author_name")),
                    new Genre(rs.getLong("genre_id"), rs.getString("genre_name")));
        }
    }
}
