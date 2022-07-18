package ru.otus.homework.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.exceptions.EntityNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Repository
public class AuthorRepositoryImpl implements AuthorRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    public AuthorRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Author saveAuthor(Author author) {
        if (author.getId() == null) {
            entityManager.persist(author);
            return author;
        }
        return entityManager.merge(author);
    }

    @Override
    public Optional<Author> getAuthorById(Long id) {
        return Optional.ofNullable(entityManager.find(Author.class, id));
    }

    @Override
    public Optional<Author> getAuthorByName(String name) {
        TypedQuery<Author> query = entityManager.createQuery("select a " +
                "from Author a " +
                "where a.name = :name", Author.class);
        query.setParameter("name", name);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Author updateAuthor(Author author) {
        Long id = author.getId();
        String name = author.getName();
        Query query = entityManager.createQuery("update Author a " +
                "set a.name = :name " +
                "where a.id = :id");
        query.setParameter("name", name);
        query.setParameter("id", id);
        query.executeUpdate();
        return getAuthorById(id).orElseThrow(() -> new EntityNotFoundException(MessageFormat.format("Запись о жанре {0} не найдена", author)));
    }

    @Override
    public void deleteAuthor(Author author) {
        Query query = entityManager.createQuery("delete from Author a where a.id = :id");
        query.setParameter("id", author.getId());
        query.executeUpdate();
    }
}
