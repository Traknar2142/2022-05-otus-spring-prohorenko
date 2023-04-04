package ru.otus.homework.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import ru.otus.homework.model.MAuthor;
import ru.otus.homework.model.MGenre;

/**
 * @author Прохоренко Виктор
 */
@Data
@NoArgsConstructor
@Document(collection = "books")
public class MBook {
    @Id
    private String id;

    private String title;

    @Field("author")
    private MAuthor author;

    @Field("genre")
    private MGenre genre;

    public MBook(String title) {
        this.title = title;
    }

    public MBook(String title, MAuthor author) {
        this.title = title;
        this.author = author;
    }

    public MBook(String title, MGenre genre) {
        this.title = title;
        this.genre = genre;
    }

    public MBook(String title, MAuthor author, MGenre genre) {
        this.title = title;
        this.author = author;
        this.genre = genre;
    }


    public MBook(String id, String title, MAuthor author, MGenre genre) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
    }


    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author=" + author +
                ", genre=" + genre +
                '}';
    }
}
