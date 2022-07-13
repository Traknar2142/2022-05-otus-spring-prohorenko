package ru.otus.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework.domain.Book;

import java.util.List;
import java.util.Optional;

/**
 * @author Прохоренко Виктор
 */
public interface BookRepository extends JpaRepository<Book, Long> {
}
