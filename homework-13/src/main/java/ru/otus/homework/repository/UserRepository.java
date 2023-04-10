package ru.otus.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework.domain.EntryUser;

import java.util.Optional;

public interface UserRepository extends JpaRepository<EntryUser, Long> {
    Optional<EntryUser> findByLogin(String login);
}
