package ru.otus.homework.transformer;

import lombok.experimental.UtilityClass;
import ru.otus.homework.domain.Book;
import ru.otus.homework.dto.BookDto;

/**
 * @author Прохоренко Виктор
 */
@UtilityClass
public class BookDtoTransformer {
    public static BookDto transformToDto(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setTitle(book.getTitle());
        bookDto.setAuthor(AuthorDtoTransformer.toDto(book.getAuthor()));
        bookDto.setGenre(GenreDtoTransformer.toDto(book.getGenre()));
        return bookDto;
    }
}
