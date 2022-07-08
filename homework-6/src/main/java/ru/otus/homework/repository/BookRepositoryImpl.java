package ru.otus.homework.repository;

import org.springframework.stereotype.Repository;
import ru.otus.homework.domain.Book;
import ru.otus.homework.exceptions.EntityNotFoundException;

import javax.persistence.EntityManager;
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
        return Optional.ofNullable(entityManager.find(Book.class, id));
    }

    @Override
    public List<Book> getAll() {
        TypedQuery<Book> query = entityManager.createQuery("select b from Book b", Book.class);
        return query.getResultList();
    }

    @Override
    public Book updateBook(Book book) {
        Long id = book.getId();
        String title = book.getTitle();
        Query query = entityManager.createQuery("update Book b " +
                "set b.title = :title, b.genre = :genre, b.author = :author " +
                "where b.id = :id");
        query.setParameter("title", title);
        query.setParameter("genre", book.getGenre());
        query.setParameter("author", book.getAuthor());
        query.setParameter("id", id);
        query.executeUpdate();
        return getBookById(id).orElseThrow(()-> new EntityNotFoundException(MessageFormat.format("Запись о книге {0} не найдена", book)));
    }

    @Override
    public void deleteBookById(Long id) {
        Query query = entityManager.createQuery("delete from Book b where b.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
