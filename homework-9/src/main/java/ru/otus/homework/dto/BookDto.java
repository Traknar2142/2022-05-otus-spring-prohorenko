package ru.otus.homework.dto;

import lombok.Data;

/**
 * @author Прохоренко Виктор
 */
@Data
public class BookDto {
    private Long id;
    private String title;
    private AuthorDto author;
    private GenreDto genre;
}
