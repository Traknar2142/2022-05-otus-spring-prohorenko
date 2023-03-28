package ru.otus.homework.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;
import javax.persistence.IdClass;

/**
 * @author Прохоренко Виктор
 */
@Data
@NoArgsConstructor
@Document(collection = "genre")
public class MGenre {

    @Id
    private String id;

    @Field("name")
    private String name;

    public MGenre(String id, String name) {
        this.id = id;
        this.name = name;
    }


    public MGenre(String id) {
        this.id = id;
    }
}
