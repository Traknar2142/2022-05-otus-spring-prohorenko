package ru.otus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.domain.User;
import ru.otus.service.TestProcessService;

/**
 * @author Прохоренко Виктор
 */
@ShellComponent
@RequiredArgsConstructor
public class ShellController {
    private final TestProcessService service;
    private User user;

    @ShellMethod(value = "Login", key = {"l", "login"})
    public String login(@ShellOption String firstName, String lastName){
        user = new User(firstName, lastName);
        return String.format("Добро пожаловать: %s %s", user.getFirstName(), user.getLastName());
    }

    @ShellMethod(value = "start", key = {"s", "start"})
    @ShellMethodAvailability(value = "isUserLogin")
    public void testProcess(){
        service.testProcess();
    }

    private Availability isUserLogin(){
        return user == null? Availability.unavailable("Сначала нужно залогиниться"):Availability.available();
    }
}
