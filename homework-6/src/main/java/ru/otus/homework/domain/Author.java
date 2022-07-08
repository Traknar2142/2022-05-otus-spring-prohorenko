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
@Table(name = "t_author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;

    public Author(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Author(String name) {
        this.name = name;
    }

    public Author(Long id) {
        this.id = id;
    }

}
