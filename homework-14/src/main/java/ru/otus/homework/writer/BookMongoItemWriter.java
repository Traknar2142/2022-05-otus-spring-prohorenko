package ru.otus.homework.writer;

import org.bson.Document;
import org.springframework.batch.item.ItemWriter;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import ru.otus.homework.model.MAuthor;
import ru.otus.homework.model.MGenre;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

public class BookMongoItemWriter implements ItemWriter<Document> {

    private final MongoTemplate mongoTemplate;

    public BookMongoItemWriter(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void write(List<? extends Document> documents) {
        for (Document document : documents) {
            Document author = (Document) document.get("author");

            Document genre = (Document) document.get("genre");

            Query authorQuery = new Query();
            authorQuery.addCriteria(where("name").is(author.getString("name")));

            Query genreQuery = new Query();
            genreQuery.addCriteria(where("name").is(genre.getString("name")));

            MAuthor mAuthor = mongoTemplate.findOne(authorQuery, MAuthor.class);

            MGenre mGenre = mongoTemplate.findOne(genreQuery, MGenre.class);

            document.put("author", mAuthor);
            document.put("genre", mGenre);
            mongoTemplate.insert(document, "book");
        }
    }
}
