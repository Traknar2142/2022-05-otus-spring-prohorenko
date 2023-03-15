package ru.otus.homework.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.service.BookService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BookController.class)
@DisplayName("Контроллер должен: ")
public class BookControllerTest {

    @MockBean
    private BookService bookService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(
            username = "user",
            password = "1234",
            authorities = {"USER"}
    )
    @DisplayName("Дать доступ к шаблонам аутентифицированным пользователям: ")
    void successAuthenticationTest() throws Exception {
        Author author = new Author(1L, "George Orwell");
        Genre genre = new Genre(1L, "Dystopia");
        Book book = new Book(1L, "1984", author, genre);
        Mockito.when(bookService.getById(Mockito.any())).thenReturn(book);

        mockMvc.perform(get("/")).andExpect(status().isOk());
        mockMvc.perform(get("/add")).andExpect(status().isOk());
        mockMvc.perform(get("/edit").param("id","1")).andExpect(status().isOk());
    }

    @Test
    @DisplayName("Не дать доступ к шаблонам не аутентифицированным пользователям: ")
    void failAuthenticationTest() throws Exception {
        Author author = new Author(1L, "George Orwell");
        Genre genre = new Genre(1L, "Dystopia");
        Book book = new Book(1L, "1984", author, genre);
        Mockito.when(bookService.getById(Mockito.any())).thenReturn(book);

        mockMvc.perform(get("/")).andExpect(status().isUnauthorized());
        mockMvc.perform(get("/add")).andExpect(status().isUnauthorized());
        mockMvc.perform(get("/edit").param("id","1")).andExpect(status().isUnauthorized());
    }
}
