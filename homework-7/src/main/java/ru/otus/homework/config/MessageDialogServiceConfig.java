package ru.otus.homework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.homework.service.MessageDialogService;
import ru.otus.homework.service.MessageDialogServiceImpl;

/**
 * @author Прохоренко Виктор
 */
@Configuration
public class MessageDialogServiceConfig {
    @Bean
    public static MessageDialogService messageDialogService(){
        return new MessageDialogServiceImpl(System.in, System.out);
    }
}
