package ru.otus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.otus.service.TestProcessService;
import ru.otus.service.TestProcessServiceImpl;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(Application.class, args);
        TestProcessServiceImpl bean = run.getBean(TestProcessServiceImpl.class);
        bean.testProcess();
    }

}
