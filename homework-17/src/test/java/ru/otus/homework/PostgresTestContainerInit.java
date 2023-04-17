package ru.otus.homework;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

public abstract class PostgresTestContainerInit {
    private static final PostgreSQLContainer<?> POSTGRE_SQL_CONTAINER = new PostgreSQLContainer<>("postgres:12");

    static {
        POSTGRE_SQL_CONTAINER.start();
    }

    @DynamicPropertySource
    public static void properties(DynamicPropertyRegistry registry){
        registry.add("database.url", () -> POSTGRE_SQL_CONTAINER.getJdbcUrl() + "&stringtype=uncpecified");
        registry.add("database.user", POSTGRE_SQL_CONTAINER::getUsername);
        registry.add("database.pwd", POSTGRE_SQL_CONTAINER::getPassword);
    }
}
