package ru.otus.domain;

import lombok.Builder;
import lombok.Data;

/**
 * @author Прохоренко Виктор
 */
@Data
@Builder
public class User {
    private String firstName;
    private String lastName;
}
