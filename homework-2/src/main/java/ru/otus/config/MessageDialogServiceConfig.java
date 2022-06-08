package ru.otus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.service.MessageDialogService;
import ru.otus.service.MessageDialogServiceImpl;

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
