package ru.otus.homework.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Прохоренко Виктор
 */
@Getter
@Setter
@NoArgsConstructor
@Document(collection = "comments")
public class Comment {
    @Id
    private String id;

    private String commentMessage;

    @DBRef
    private Book book;

    public Comment(String commentMessage) {
        this.commentMessage = commentMessage;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", commentMessage='" + commentMessage + '\'' +
                '}';
    }
}
