package ru.otus.homework.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BookController {

    @GetMapping("/")
    public String listPage(Model model) {
        model.addAttribute("keywords", "some keywords");
        return "list";
    }

}
