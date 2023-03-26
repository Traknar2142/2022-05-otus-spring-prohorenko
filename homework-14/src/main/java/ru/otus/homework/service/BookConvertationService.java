package ru.otus.homework.service;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import ru.otus.homework.model.Book;

@Service
public class BookConvertationService {
    private final MongoTemplate mongoTemplate;

    public BookConvertationService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public Document convertToDoc(Book book){
        Document bookDoc = new Document();
        bookDoc.put("title", book.getTitle());

        Document author = new Document();
        author.put("_id", new ObjectId());
        author.put("name", book.getAuthor().getName());

        mongoTemplate.insert(author, "author").getObjectId("_id");

        Document genre = new Document();
        genre.put("_id", new ObjectId());
        genre.put("name", book.getGenre().getName());

        mongoTemplate.insert(genre, "genre").getObjectId("_id");


        bookDoc.put("author", author);
        bookDoc.put("genre", genre);

        return bookDoc;
    }
}
