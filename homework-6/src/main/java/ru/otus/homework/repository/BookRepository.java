package ru.otus.homework.repository;

import ru.otus.homework.domain.Book;

import java.util.List;
import java.util.Optional;

/**
 * @author Прохоренко Виктор
 */
public interface BookRepository {
    Book saveBook(Book book);
    Optional<Book> getBookById(Long id);
    List<Book> getAll();
    Book updateBook(Book book);
    void deleteBookById(Long id);
}
