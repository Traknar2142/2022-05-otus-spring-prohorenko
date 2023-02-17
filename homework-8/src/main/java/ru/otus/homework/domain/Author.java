package ru.otus.homework.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Прохоренко Виктор
 */
@Data
@NoArgsConstructor
@Document(collection = "authors")
public class Author {
    @Id
    private String id;

    private String name;

    public Author(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Author(String name) {
        this.name = name;
    }

}
