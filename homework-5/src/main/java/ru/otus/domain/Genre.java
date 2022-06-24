package ru.otus.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Прохоренко Виктор
 */
@Data
@AllArgsConstructor
public class Genre {
    private Long id;
    private String name;
}
