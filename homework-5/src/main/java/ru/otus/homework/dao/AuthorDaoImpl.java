package ru.otus.homework.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.homework.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Objects;

@Repository
public class AuthorDaoImpl implements AuthorDao{
    private final NamedParameterJdbcOperations jdbcTemplate;

    public AuthorDaoImpl(NamedParameterJdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Author saveAuthor(Author author) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("name", author.getName());
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update("INSERT INTO t_author(name) values (:name)",
                parameters, keyHolder);
        return getAuthorById(Objects.requireNonNull(keyHolder.getKey()).longValue());
    }

    @Override
    public Author getAuthorById(Long id) {
        return jdbcTemplate.queryForObject("SELECT id, name FROM t_author WHERE id = :id",
                Map.of("id", id), new AuthorMapper());
    }

    @Override
    public Author updateAuthor(Author author) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", author.getId());
        parameters.addValue("name", author.getName());
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update("UPDATE t_author SET name = :name WHERE id = :id",
                parameters, keyHolder);
        return getAuthorById(Objects.requireNonNull(keyHolder.getKey()).longValue());
    }

    @Override
    public void deleteAuthor(Author author) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", author.getId());
        jdbcTemplate.update("DELETE FROM t_author WHERE id = :id", parameters);
    }

    private static class AuthorMapper implements RowMapper<Author>{
        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Author(rs.getLong("id"), rs.getString("name"));
        }
    }
}
