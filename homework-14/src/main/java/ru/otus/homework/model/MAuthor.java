package ru.otus.homework.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;

/**
 * @author Прохоренко Виктор
 */
@Data
@NoArgsConstructor
@Document(collection = "author")
public class MAuthor {
    @Id
    private String id;
    @Field("name")
    private String name;

    public MAuthor(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public MAuthor(String name) {
        this.name = name;
    }

}
