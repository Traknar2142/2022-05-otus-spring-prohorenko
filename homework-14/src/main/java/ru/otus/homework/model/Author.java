package ru.otus.homework.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Прохоренко Виктор
 */
@Data
@NoArgsConstructor
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

}
