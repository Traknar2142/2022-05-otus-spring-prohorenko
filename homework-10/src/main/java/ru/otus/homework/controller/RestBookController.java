package ru.otus.homework.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.homework.domain.Book;
import ru.otus.homework.dto.BookDto;
import ru.otus.homework.service.BookService;
import ru.otus.homework.transformer.BookDtoTransformer;
import ru.otus.homework.transformer.BookTransformer;

import java.util.List;

/**
 * @author Прохоренко Виктор
 */
@RequiredArgsConstructor
@RestController
public class RestBookController {
    private final BookService bookService;

    @GetMapping("/api/books")
    public List<BookDto> getAllBooks(){
        return bookService.getAllBooks();
    }

    @GetMapping("/api/books/{id}")
    public ResponseEntity<BookDto> getBook(@PathVariable Long id){
        Book entityBook = bookService.getById(id);
        return ResponseEntity.ok(BookDtoTransformer.transformToDto(entityBook));
    }

    @PutMapping(value ="/api/books/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces =
            MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookDto> updateBook(@RequestBody BookDto bookDto){
        Book entityBook = BookDtoTransformer.transformToEntity(bookDto);
        Book book = bookService.updateBook(entityBook);
        return ResponseEntity.ok(BookDtoTransformer.transformToDto(book));
    }

    @DeleteMapping("/api/books/delete/{id}")
    public void deleteBook(@PathVariable Long id){
        bookService.deleteBook(id);
    }
}
