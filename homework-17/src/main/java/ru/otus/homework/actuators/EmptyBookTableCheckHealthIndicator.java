package ru.otus.homework.actuators;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;
import ru.otus.homework.service.BookService;

@Component
public class EmptyBookTableCheckHealthIndicator implements HealthIndicator {
    private final BookService bookService;

    public EmptyBookTableCheckHealthIndicator(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public Health health() {
        if (isBookRepoEmpty()) {
            return Health.down()
                    .status(Status.DOWN)
                    .withDetail("message", "Все книжки расхватали!")
                    .build();
        } else {
            return Health.up()
                    .status(Status.UP)
                    .withDetail("message", "Книги на месте")
                    .build();
        }
    }

    private boolean isBookRepoEmpty() {
        long countOfBooks = bookService.getCountOfBooks();
        return countOfBooks == 0;
    }
}
