package ru.otus.homework.service;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import ru.otus.homework.model.Book;

@Service
public class BookConvertationService {

    public Document process(Book book) {
        Document bookDoc = new Document();
        bookDoc.put("title", book.getTitle());

        Document author = new Document();
        author.put("_id", new ObjectId());
        author.put("name", book.getAuthor().getName());

        Document genre = new Document();
        genre.put("_id", new ObjectId());
        genre.put("name", book.getGenre().getName());


        bookDoc.put("author", author);
        bookDoc.put("genre", genre);
        return bookDoc;
    }
}

