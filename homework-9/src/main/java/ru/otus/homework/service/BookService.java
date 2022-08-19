package ru.otus.homework.service;

import ru.otus.homework.domain.Book;
import ru.otus.homework.dto.BookDto;

import java.util.List;

/**
 * @author Прохоренко Виктор
 */
public interface BookService {
    List<BookDto> getAllBooks();
    void printBookById(Long id);
    Book addBook(Book book);
    Book getById(Long id);
    Book updateBook(Book book);
    void deleteBook(Long id);
}
