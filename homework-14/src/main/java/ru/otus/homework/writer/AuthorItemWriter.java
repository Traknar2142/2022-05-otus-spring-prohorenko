package ru.otus.homework.writer;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.batch.item.ItemWriter;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.homework.model.Author;

import java.util.List;

public class AuthorItemWriter implements ItemWriter<Author> {

    private final MongoTemplate mongoTemplate;

    public AuthorItemWriter(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void write(List<? extends Author> authors) throws Exception {
        for (Author author : authors) {
            Document document = new Document();
            ObjectId id = new ObjectId();
            document.put("_id", id);
            document.put("name", author.getName());
            mongoTemplate.insert(document, "author");
        }
    }
}
