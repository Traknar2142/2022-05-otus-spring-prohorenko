package ru.otus.domain;

import lombok.Data;

/**
 * @author Прохоренко Виктор
 */
@Data
public class User {
    private String firstName;
    private String lastName;

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
