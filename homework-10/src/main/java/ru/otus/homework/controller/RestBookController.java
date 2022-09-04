package ru.otus.homework.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.homework.dto.BookDto;
import ru.otus.homework.service.BookService;

import java.util.List;

/**
 * @author Прохоренко Виктор
 */
@RequiredArgsConstructor
@RestController
public class RestBookController {
    private final BookService bookService;

    @GetMapping("/api/books")
    public List<BookDto> getAllBooks() {
        return bookService.getAllBooks();
    }

}
