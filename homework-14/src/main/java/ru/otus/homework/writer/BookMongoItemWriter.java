package ru.otus.homework.writer;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.batch.item.ItemWriter;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.homework.model.Author;
import ru.otus.homework.model.Genre;

import java.util.List;

public class BookMongoItemWriter implements ItemWriter<Document> {

    private final MongoTemplate mongoTemplate;

    public BookMongoItemWriter(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void write(List<? extends Document> documents) throws Exception {
        for (Document document : documents) {
            String authorId = document.getString("author_id");
            String genreId = document.getString("genre_id");

            Author author = mongoTemplate.findById(new ObjectId(authorId), Author.class);
            Genre genre = mongoTemplate.findById(new ObjectId(genreId), Genre.class);

            document.put("author", new Document("_id", author.getId()).append("name", author.getName()));
            document.put("genre", new Document("_id", genre.getId()).append("name", genre.getName()));
            mongoTemplate.insert(document, "book");
        }
    }
}
