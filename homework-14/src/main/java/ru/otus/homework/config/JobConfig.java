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
import ru.otus.homework.rowmapper.BookRowMapper;
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

    private static final String BOOK_SELECT_WITH_RELATIONS =
            "SELECT t_book.id, title, author_id, genre_id, t_author.name as author_name, t_genre.name as genre_name " +
                    "FROM t_book " +
                    "LEFT JOIN t_author " +
                    "ON t_book.author_id = t_author.id " +
                    "LEFT JOIN t_genre " +
                    "ON t_book.genre_id = t_genre.id ";
    private static final String GENRE_SELECT = "SELECT t_genre.id, t_genre.name FROM t_genre";
    private static final String AUTHOR_SELECT = "SELECT t_author.id, t_author.name FROM t_author";

    private static final int CHUNK_SIZE = 1;

    @Bean
    public JdbcCursorItemReader<Book> bookReader() {
        JdbcCursorItemReader<Book> reader = new JdbcCursorItemReader<>();
        reader.setDataSource(dataSource);
        reader.setSql(BOOK_SELECT_WITH_RELATIONS);
        reader.setRowMapper(new BookRowMapper());
        return reader;
    }

    @Bean
    public JdbcCursorItemReader<Genre> genreReader() {
        JdbcCursorItemReader<Genre> reader = new JdbcCursorItemReader<>();
        reader.setDataSource(dataSource);
        reader.setSql(GENRE_SELECT);
        reader.setRowMapper(new BeanPropertyRowMapper<>(Genre.class));
        return reader;
    }

    @Bean
    public JdbcCursorItemReader<Author> authorReader() {
        JdbcCursorItemReader<Author> reader = new JdbcCursorItemReader<>();
        reader.setDataSource(dataSource);
        reader.setSql(AUTHOR_SELECT);
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
                .<Author, Author>chunk(CHUNK_SIZE)
                .reader(authorReader())
                .writer(authorItemWriter())
                .build();
    }

    @Bean
    public Step genreStep() {
        return stepBuilderFactory.get("genreStep")
                .<Genre, Genre>chunk(CHUNK_SIZE)
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
