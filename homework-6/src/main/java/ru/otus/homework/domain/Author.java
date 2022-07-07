package ru.otus.homework.domain;

import lombok.Data;

/**
 * @author Прохоренко Виктор
 */
@Data
public class Author {
    private Long id;
    private String name;

    public Author(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Author(String name) {
        this.name = name;
    }

    public Author(Long id) {
        this.id = id;
    }
}
