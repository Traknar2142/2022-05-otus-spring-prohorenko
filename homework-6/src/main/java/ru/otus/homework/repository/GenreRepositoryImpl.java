package ru.otus.homework.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.homework.domain.Comment;
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

/**
 * @author Прохоренко Виктор
 */
@Repository
public class GenreRepositoryImpl implements GenreRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    public GenreRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Genre saveGenre(Genre genre) {
        if (genre.getId() == null){
            entityManager.persist(genre);
            return genre;
        }
        return entityManager.merge(genre);
    }

    @Override
    public Optional<Genre> getGenreById(Long id) {
        return Optional.ofNullable(entityManager.find(Genre.class, id));
    }

    @Override
    public Optional<Genre> getGenreByName(String name) {
        TypedQuery<Genre> query = entityManager.createQuery("select g " +
                "from Genre g " +
                "where g.name = :name", Genre.class);
        query.setParameter("name", name);
        try{
            Optional<Genre> singleResult = Optional.of(query.getSingleResult());
            return singleResult;
        }catch (Exception e){
            return Optional.empty();
        }
    }

    @Override
    public Genre updateGenre(Genre genre)  {
        Long id = genre.getId();
        String name = genre.getName();
        Query query = entityManager.createQuery("update Genre  g " +
                "set g.name = :name " +
                "where g.id = :id");
        query.setParameter("name", name);
        query.setParameter("id", id);
        query.executeUpdate();
        return getGenreById(id).orElseThrow(()-> new EntityNotFoundException(MessageFormat.format("Запись о жанре {0} не найдена", genre)));
    }

    @Override
    public void deleteGenre(Genre genre) {
        Query query = entityManager.createQuery("delete from Genre  g where g.id = :id");
        query.setParameter("id", genre.getId());
        query.executeUpdate();
    }
}
