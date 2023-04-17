package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.dto.BookDto;
import ru.otus.homework.exceptions.EntityNotFoundException;
import ru.otus.homework.repository.AuthorRepository;
import ru.otus.homework.repository.BookRepository;
import ru.otus.homework.repository.GenreRepository;
import ru.otus.homework.transformer.BookDtoTransformer;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Прохоренко Виктор
 */
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepositoryDao) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepositoryDao;
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookDto> getAllBooks() {
        List<Book> allBooks = bookRepository.getAll();
        return transformListToDto(allBooks);
    }

    private List<BookDto> transformListToDto(List<Book> allBooks) {
        return allBooks.stream().map(BookDtoTransformer::transformToDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public Book getById(Long id) {
        return bookRepository.getBookById(id).orElseThrow(() -> new EntityNotFoundException(MessageFormat.format("Запись о книге с id {0} не найдена", id)));
    }

    @Transactional
    @Override
    public Book addBook(Book book) {
        updateGenreForBook(book);
        updateAuthorForBook(book);
        return bookRepository.save(book);
    }

    @Transactional
    @Override
    public Book updateBook(Book book) {
        updateGenreForBook(book);
        updateAuthorForBook(book);
        return bookRepository.save(book);
    }

    @Transactional
    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public long getCountOfBooks(){
        return bookRepository.count();
    }

    private void updateGenreForBook(Book book) {
        if (book.getGenre() != null) {
            Genre genreByName = getOrSaveGenre(book.getGenre().getName());
            book.setGenre(genreByName);
        }
    }

    private void updateAuthorForBook(Book book) {
        if (book.getAuthor() != null) {
            Author authorByName = getOrSaveAuthor(book.getAuthor().getName());
            book.setAuthor(authorByName);
        }
    }

    private Genre getOrSaveGenre(String name) {
        Optional<Genre> genreByName = genreRepository.findByName(name);
        if (genreByName.isEmpty()){
            return genreRepository.save(new Genre(name));
        }
        return genreByName.get();
    }

    private Author getOrSaveAuthor(String name) {
        Optional<Author> authorByName = authorRepository.findByName(name);
        if (authorByName.isEmpty()){
            return authorRepository.save(new Author(name));
        }
        return authorByName.get();
    }

}
