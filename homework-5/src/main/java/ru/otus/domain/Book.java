package ru.otus.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Прохоренко Виктор
 */
@Data
@AllArgsConstructor
public class Book {
   private Long id;
   private String title;
   private Author author;
   private Genre genre;
}
