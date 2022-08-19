package ru.otus.homework.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.homework.domain.Book;
import ru.otus.homework.dto.BookDto;
import ru.otus.homework.service.BookService;

import java.util.List;

/**
 * @author Прохоренко Виктор
 */
@Controller
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/")
    public String listPage(Model model){
        List<BookDto> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "list";
    }
}
