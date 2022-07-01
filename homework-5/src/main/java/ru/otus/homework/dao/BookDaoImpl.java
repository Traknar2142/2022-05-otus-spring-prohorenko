package ru.otus.homework.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Objects;

@Repository
public class BookDaoImpl implements BookDao{
    private final AuthorDao authorDao;
    private final GenreDao genreDao;
    private final NamedParameterJdbcOperations jdbcTemplate;

    public BookDaoImpl(AuthorDao authorDao, GenreDao genreDao, NamedParameterJdbcOperations jdbcTemplate) {
        this.authorDao = authorDao;
        this.genreDao = genreDao;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Book saveBook(Book book) {
        Author savedAuthor = authorDao.saveAuthor(book.getAuthor());
        Genre savedGenre = genreDao.saveGenre(book.getGenre());

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("title", book.getTitle());
        parameters.addValue("author_id", savedAuthor.getId());
        parameters.addValue("genre_id", savedGenre.getId());
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update("INSERT INTO t_book(title, author_id, genre_id) values (:title, :author_id, :genre_id)",
                parameters, keyHolder);
        return getBookById(Objects.requireNonNull(keyHolder.getKey()).longValue());
    }

    @Override
    public Book getBookById(Long id) {
        Book bookFromDb = jdbcTemplate.queryForObject("SELECT * FROM t_book WHERE id = :id",
                Map.of("id", id), new BookMapper());
        Long authorId = bookFromDb.getAuthor().getId();
        Long genreId = bookFromDb.getGenre().getId();

        bookFromDb.setAuthor(authorDao.getAuthorById(authorId));
        bookFromDb.setGenre(genreDao.getGenreById(genreId));

        return bookFromDb;
    }

    @Override
    public Book updateBook(Book book) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", book.getId());
        parameters.addValue("title", book.getTitle());
        parameters.addValue("author_id", book.getAuthor().getId());
        parameters.addValue("genre_id", book.getGenre().getId());
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update("UPDATE t_book SET title = :title, author_id = :author_id, genre_id = :genre_id WHERE id = :id",
                parameters, keyHolder);
        return getBookById(Objects.requireNonNull(keyHolder.getKey()).longValue());
    }

    @Override
    public void deleteBook(Book book) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", book.getId());
        jdbcTemplate.update("DELETE FROM t_book WHERE id = :id", parameters);
    }

    private static class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Book(rs.getLong("id"),
                    rs.getString("title"),
                    new Author(rs.getLong("author_id")),
                    new Genre(rs.getLong("genre_id")));
        }
    }
}
