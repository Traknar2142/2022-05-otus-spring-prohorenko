package ru.otus.homework.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import com.mongodb.client.MongoDatabase;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.domain.Genre;

@ChangeLog
public class DatabaseChangelog {
    @ChangeSet(order = "001", id = "dropDb", author = "stvort", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "insertBook1984", author = "vprokhorenko")
    public void insertBook1984(MongockTemplate mongockTemplate) {
        Author author = new Author( "George Orwell");
        mongockTemplate.save(author, "authors");

        Genre genre = new Genre("Dystopia");
        mongockTemplate.save(genre, "genres");

        Book book = new Book("1984", author, genre);
        mongockTemplate.save(book, "books");

        Comment comment = new Comment("comment1");
        comment.setBook(book);

        mongockTemplate.save(comment, "comments");

    }

    @ChangeSet(order = "003", id = "insertBookFahrenheit_451", author = "vprokhorenko")
    public void insertBookFahrenheit_451(MongockTemplate mongockTemplate) {
        Author author = new Author( "Raymond Douglas Bradbury");
        mongockTemplate.save(author, "authors");

        Genre genre = new Genre("Psychology");
        mongockTemplate.save(genre, "genres");

        Book book = new Book("Fahrenheit 451", author, genre);
        mongockTemplate.save(book, "books");

    }

    @ChangeSet(order = "004", id = "insertBookThe_Time_Machine", author = "vprokhorenko")
    public void insertBookThe_Time_Machine(MongockTemplate mongockTemplate) {
        Author author = new Author( "H. G. Wells");
        mongockTemplate.save(author, "authors");

        Genre genre = new Genre("Fiction");
        mongockTemplate.save(genre, "genres");

        Book book = new Book("The Time Machine", author, genre);
        mongockTemplate.save(book, "books");
    }

    @ChangeSet(order = "005", id = "insertBookThe_Castle", author = "vprokhorenko")
    public void insertBookThe_Castle(MongockTemplate mongockTemplate) {
        Author author = new Author( "Franz Kafka");
        mongockTemplate.save(author, "authors");

        Genre genre = new Genre("Novel");
        mongockTemplate.save(genre, "genres");

        Book book = new Book( "The Castle", author, genre);
        mongockTemplate.save(book, "books");
    }
}
