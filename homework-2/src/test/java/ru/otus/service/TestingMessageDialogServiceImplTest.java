package ru.otus.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Прохоренко Виктор
 */
@DisplayName("Сервис ввода/вывода сообщений должен")
public class TestingMessageDialogServiceImplTest {
    private TestingMessageDialogServiceImpl testingMessageDialogService;

    @BeforeEach
    void setUp(){
        testingMessageDialogService = new TestingMessageDialogServiceImpl(System.in);
    }

    @Test
    @DisplayName("Читать сроку из входного потока")
    void shouldReadStringInputStream(){
        String expected = "some_string";
        ByteArrayInputStream in = new ByteArrayInputStream("some_string".getBytes());
        testingMessageDialogService = new TestingMessageDialogServiceImpl(in);
        System.setIn(in);
        String actual = testingMessageDialogService.inputMessage();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Вывести строку исходящего потока")
    void shouldReturnStringOutputStream(){
        String expected = "some_string";
        String actual = testingMessageDialogService.outputMessage(expected);
        assertThat(actual).isEqualTo(expected);
    }
}
