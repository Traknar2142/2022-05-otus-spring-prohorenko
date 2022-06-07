package ru.otus.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Прохоренко Виктор
 */
@DisplayName("Сервис ввода/вывода сообщений должен")
@ExtendWith(MockitoExtension.class)
public class MessageDialogServiceImplTest {
    private InputStream in;
    @Mock
    private PrintStream output;

    private MessageDialogServiceImpl messageDialogService;


    @BeforeEach
    void setUp() {
        in = new ByteArrayInputStream("some_string".getBytes());
        messageDialogService = new MessageDialogServiceImpl(in, output);
    }

    @Test
    @DisplayName("Читать сроку из входного потока")
    void shouldReadStringInputStream() {
        String expected = "some_string";
        String actual = messageDialogService.inputMessage();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Вывести строку исходящего потока")
    void shouldReturnStringOutputStream() {
        String expected = "some_string";
        String actual = messageDialogService.outputMessage(expected);
        assertThat(actual).isEqualTo(expected);
    }
}
