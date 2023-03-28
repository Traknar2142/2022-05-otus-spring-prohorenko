package ru.otus.homework.config;

import org.bson.Document;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import ru.otus.homework.model.Author;
import ru.otus.homework.model.Book;
import ru.otus.homework.model.Genre;
import ru.otus.homework.service.BookConvertationService;
import ru.otus.homework.writer.AuthorItemWriter;
import ru.otus.homework.writer.BookMongoItemWriter;
import ru.otus.homework.writer.GenreItemWriter;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class JobConfig {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private MongoOperations mongoOperations;

    @Autowired
    private MongoTemplate mongoTemplate;

    private static final int CHUNK_SIZE = 1;

    @Bean
    public JdbcCursorItemReader<Book> bookReader() {
        JdbcCursorItemReader<Book> reader = new JdbcCursorItemReader<>();
        reader.setDataSource(dataSource);
        reader.setSql("SELECT t_book.id, title, author_id, genre_id FROM t_book");
        reader.setRowMapper(new BeanPropertyRowMapper<>(Book.class));
        return reader;
    }

    @Bean
    public JdbcCursorItemReader<Genre> genreReader() {
        JdbcCursorItemReader<Genre> reader = new JdbcCursorItemReader<>();
        reader.setDataSource(dataSource);
        reader.setSql("SELECT t_genre.id, t_genre.name FROM t_genre");
        reader.setRowMapper(new BeanPropertyRowMapper<>(Genre.class));
        return reader;
    }

    @Bean
    public JdbcCursorItemReader<Author> authorReader() {
        JdbcCursorItemReader<Author> reader = new JdbcCursorItemReader<>();
        reader.setDataSource(dataSource);
        reader.setSql("SELECT t_author.id, t_author.name FROM t_author");
        reader.setRowMapper(new BeanPropertyRowMapper<>(Author.class));
        return reader;
    }

    @Bean
    public AuthorItemWriter authorItemWriter(){
        return new AuthorItemWriter(mongoTemplate);
    }

    @Bean
    public GenreItemWriter genreItemWriter(){
        return new GenreItemWriter(mongoTemplate);
    }

    @Bean
    public BookMongoItemWriter bookMongoItemWriter(){
        return new BookMongoItemWriter(mongoTemplate);
    }

    @Bean
    public Job transferDataJob(ItemProcessor<Book, Document> processor) {
        return jobBuilderFactory.get("transferDataJob")
                .incrementer(new RunIdIncrementer())
                .start(authorStep())
                .next(genreStep())
                .next(bookStep(processor))
                .build();
    }

    @Bean
    public ItemProcessor<Book, Document> processor(BookConvertationService convertationService) {
        return convertationService::process;
    }

    @Bean
    public Step authorStep() {
        return stepBuilderFactory.get("authorStep")
                .<Author, Author>chunk(10)
                .reader(authorReader())
                .writer(authorItemWriter())
                .build();
    }

    @Bean
    public Step genreStep() {
        return stepBuilderFactory.get("genreStep")
                .<Genre, Genre>chunk(10)
                .reader(genreReader())
                .writer(genreItemWriter())
                .build();
    }

    @Bean
    public Step bookStep(ItemProcessor<Book, Document> processor) {
        return stepBuilderFactory.get("transferDataStep")
                .<Book, Document>chunk(CHUNK_SIZE)
                .reader(bookReader())
                .processor(processor)
                .writer(bookMongoItemWriter())
                .build();
    }
}
