package ru.otus.homework.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Прохоренко Виктор
 */
@Data
@NoArgsConstructor
public class Genre {

    private String id;

    private String name;

    public Genre(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Genre(String name) {
        this.name = name;
    }

}
