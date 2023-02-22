package ru.otus.homework.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.homework.domain.Book;

import java.util.List;
import java.util.Optional;

/**
 * @author Прохоренко Виктор
 */
public interface BookRepository extends CrudRepository<Book, String>, BookRepositoryCustom {
    @Override
    Optional<Book> findById(String s);

    @Override
    List<Book> findAll();
}
