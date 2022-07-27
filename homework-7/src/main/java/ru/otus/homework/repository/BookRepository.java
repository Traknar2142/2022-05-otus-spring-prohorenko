package ru.otus.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.otus.homework.domain.Book;

import java.util.List;
import java.util.Optional;

/**
 * @author Прохоренко Виктор
 */
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("select b from Book b join fetch b.author join fetch b.genre where b.id = :id")
    Optional<Book> getBookById(Long id);

    @Query("select b from Book b join fetch b.author join fetch b.genre ")
    List<Book> getAll();
}
