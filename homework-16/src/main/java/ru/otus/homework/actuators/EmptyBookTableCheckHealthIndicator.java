package ru.otus.homework.actuators;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;
import ru.otus.homework.domain.Book;
import ru.otus.homework.repository.BookRepository;

import java.util.List;

@Component
public class EmptyBookTableCheckHealthIndicator implements HealthIndicator {
    private final BookRepository bookRepository;

    public EmptyBookTableCheckHealthIndicator(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
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
        List<Book> all = bookRepository.findAll();
        return all.isEmpty();
    }
}
