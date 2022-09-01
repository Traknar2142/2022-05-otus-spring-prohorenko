package ru.otus.homework.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.homework.domain.Book;
import ru.otus.homework.dto.BookDto;
import ru.otus.homework.service.BookService;
import ru.otus.homework.transformer.BookDtoTransformer;

import java.util.List;

@Controller
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/")
    public String listPage(Model model) {
        model.addAttribute("keywords", "list users in Omsk, omsk, list users, list users free");
        return "list";
    }

    @GetMapping("/edit")
    public String editPage(@RequestParam("id") Long id, Model model){
        Book book = bookService.getById(id);
        model.addAttribute("book", book);
        return "edit";
    }

    /*@PostMapping("/edit")
    public String updateBook(BookDto book){
        Book entityBook = BookDtoTransformer.transformToEntity(book);
        bookService.updateBook(entityBook);
        return "redirect:/";
    }

    @GetMapping("/add")
    public String addPage(Model model){
        model.addAttribute("book", new Book());
        return "add";
    }

    @PostMapping("/add")
    public String saveBook(@ModelAttribute BookDto book){
        Book entityBook = BookDtoTransformer.transformToEntity(book);
        bookService.addBook(entityBook);
        return "redirect:/";
    }

    @GetMapping("/delete")
    public String deleteBook(@RequestParam("id") Long id){
        bookService.deleteBook(id);
        return "redirect:/";
    }*/

}
