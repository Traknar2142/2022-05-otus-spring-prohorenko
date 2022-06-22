package ru.otus.domain;

import lombok.Data;

/**
 * @author Прохоренко Виктор
 */
@Data
public class Book {
   private String title;
   private Author author;
   private Genre genre;
}
