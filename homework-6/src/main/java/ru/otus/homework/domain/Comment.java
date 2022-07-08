package ru.otus.homework.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Прохоренко Виктор
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "t_comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "comment")
    private String commentMessage;

    public Comment(Long id, String commentMessage) {
        this.id = id;
        this.commentMessage = commentMessage;
    }

    public Comment(String commentMessage) {
        this.commentMessage = commentMessage;
    }
}
