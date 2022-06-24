package ru.otus.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Objects;

@Repository
public class AuthorDaoImpl implements AuthorDao{
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public AuthorDaoImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Author saveAuthor(Author author) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("name", author.getName());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update("INSERT INTO t_author(name) values (:name)",
                parameters, keyHolder);
        author.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return author;
    }

    @Override
    public Author getAuthorById(Long id) {
        return jdbcTemplate.queryForObject("SELECT id, name FROM t_author WHERE id = :id",
                Map.of("id", id), new AuthorMapper());
    }

    @Override
    public Author updateAuthor(Author author) {
        return null;
    }

    @Override
    public void deleteAuthor(Author author) {

    }

    private static class AuthorMapper implements RowMapper<Author>{
        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Author(rs.getLong("id"), rs.getString("name"));
        }
    }
}
