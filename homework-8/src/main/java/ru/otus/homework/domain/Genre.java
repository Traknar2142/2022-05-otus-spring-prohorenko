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
@Document(collection = "genres")
public class Genre {
    @Id
    private String id;
    private String name;

    public Genre(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Genre(String genreName) {
        this.name = genreName;
    }
}
