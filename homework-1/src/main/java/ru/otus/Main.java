package ru.otus;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.service.TestProcessService;
import ru.otus.service.TestProcessServiceImpl;


/**
 * @author Прохоренко Виктор
 */
public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        TestProcessService testProcessService = context.getBean(TestProcessServiceImpl.class);
        testProcessService.testProcess();
    }
}