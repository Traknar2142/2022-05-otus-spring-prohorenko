package ru.otus.homework.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Прохоренко Виктор
 */
@Data
public class Genre {
    private Long id;
    private String name;

    public Genre(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Genre(String name) {
        this.name = name;
    }

    public Genre(Long id) {
        this.id = id;
    }
}
