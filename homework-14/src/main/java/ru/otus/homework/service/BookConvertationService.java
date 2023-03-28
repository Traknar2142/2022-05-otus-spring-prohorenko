package ru.otus.homework.service;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import ru.otus.homework.model.Book;

@Service
public class BookConvertationService {

    public Document process(Book book) {
        Document document = new Document();
        ObjectId id = new ObjectId();
        document.put("_id", id);
        document.put("title", book.getTitle());
        document.put("author_id", book.getAuthor().getId());
        document.put("genre_id", book.getGenre().getId());

        return document;
    }
}

