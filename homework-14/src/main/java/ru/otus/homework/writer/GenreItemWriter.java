package ru.otus.homework.writer;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.batch.item.ItemWriter;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.homework.model.Genre;

import java.util.List;

public class GenreItemWriter implements ItemWriter<Genre> {
    private final MongoTemplate mongoTemplate;

    public GenreItemWriter(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void write(List<? extends Genre> genres) throws Exception {
        for (Genre genre : genres) {
            Document document = new Document();
            ObjectId id = new ObjectId();
            document.put("_id", id);
            document.put("name", genre.getName());
            mongoTemplate.insert(document, "genre");
        }
    }
}
