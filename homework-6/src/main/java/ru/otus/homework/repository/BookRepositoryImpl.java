package ru.otus.homework.repository;

import org.springframework.stereotype.Repository;
import ru.otus.homework.domain.Book;
import ru.otus.homework.exceptions.EntityNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@Repository
public class BookRepositoryImpl implements BookRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    public BookRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Book saveBook(Book book) {
        if (book.getId() == null){
            entityManager.persist(book);
            return book;
        }
        return entityManager.merge(book);
    }

    @Override
    public Optional<Book> getBookById(Long id) {
        TypedQuery<Book> query = entityManager.createQuery("select b from Book b " +
                "join fetch b.author " +
                "join  fetch b.genre " +
                "where b.id = :id", Book.class);
        query.setParameter("id", id);

        try{
            return Optional.of(query.getSingleResult());
        }catch (NoResultException e){
            return Optional.empty();
        }

    }

    @Override
    public List<Book> getAll() {
        TypedQuery<Book> query = entityManager.createQuery("select b from Book b " +
                "join fetch b.author " +
                "join fetch b.genre", Book.class);
        return query.getResultList();
    }

    @Override
    public Book updateBook(Book book) {
        return entityManager.merge(book);
    }

    @Override
    public void deleteBookById(Long id) {
        Query query = entityManager.createQuery("delete from Book b where b.id = :id");
        query.setParameter("id", id);
        int i = query.executeUpdate();
        if (i==0) throw new EntityNotFoundException(MessageFormat.format("Запись {0} не найдена", id));
    }
}
