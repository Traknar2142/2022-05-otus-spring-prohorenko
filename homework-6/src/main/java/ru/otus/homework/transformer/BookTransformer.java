package ru.otus.homework.transformer;

import lombok.experimental.UtilityClass;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;

/**
 * @author Прохоренко Виктор
 */
@UtilityClass
public class BookTransformer {
    public static Book transformForAdd(String title, String authorName, String genreName){
        Genre genre = new Genre(genreName);
        Author author = new Author(authorName);
        return new Book(title, author, genre);
    }

    public static Book transformForUpdate(Book book, String title, String authorName, String genreName){
        Genre genre = new Genre(genreName);
        Author author = new Author(authorName);
        book.setTitle(title);
        book.setGenre(genre);
        book.setAuthor(author);
        return book;
    }
}
