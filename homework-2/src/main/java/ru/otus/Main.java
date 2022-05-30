package ru.otus;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.service.TestProcessService;
import ru.otus.service.TestProcessServiceImpl;


/**
 * @author Прохоренко Виктор
 */
@Configuration
@ComponentScan
@PropertySource("classpath:application.properties")
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        TestProcessService testProcessService = context.getBean(TestProcessService.class);
        testProcessService.testProcess();
    }

}