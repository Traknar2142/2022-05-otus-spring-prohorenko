package ru.otus.homework.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.MongoItemWriter;
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
import ru.otus.homework.model.MAuthor;
import ru.otus.homework.model.MBook;
import ru.otus.homework.model.MGenre;
import ru.otus.homework.rowmapper.BookRowMapper;
import ru.otus.homework.service.AuthorConvertationService;
import ru.otus.homework.service.BookConvertationService;
import ru.otus.homework.service.GenreComvertationService;

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
    public Job transferDataJob(ItemProcessor<Book, MBook> bookProcessor,
                               ItemProcessor<Author, MAuthor> authorProcessor,
                               ItemProcessor<Genre, MGenre> genreProcessor) {
        return jobBuilderFactory.get("transferDataJob")
                .incrementer(new RunIdIncrementer())
                .start(authorStep(authorProcessor))
                .next(genreStep(genreProcessor))
                .next(bookStep(bookProcessor))
                .build();
    }

    @Bean
    public Step authorStep(ItemProcessor<Author, MAuthor> authorProcessor) {
        return stepBuilderFactory.get("authorStep")
                .<Author, MAuthor>chunk(CHUNK_SIZE)
                .reader(authorReader())
                .processor(authorProcessor)
                .writer(authorItemWriter())
                .build();
    }

    @Bean
    public Step genreStep(ItemProcessor<Genre, MGenre> authorProcessor) {
        return stepBuilderFactory.get("genreStep")
                .<Genre, MGenre>chunk(CHUNK_SIZE)
                .reader(genreReader())
                .processor(authorProcessor)
                .writer(genreItemWriter())
                .build();
    }

    @Bean
    public Step bookStep(ItemProcessor<Book, MBook> bookProcessor) {
        return stepBuilderFactory.get("transferDataStep")
                .<Book, MBook>chunk(CHUNK_SIZE)
                .reader(bookReader())
                .processor(bookProcessor)
                .writer(bookItemWriter())
                .build();
    }

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
    public MongoItemWriter<MAuthor> authorItemWriter() {
        MongoItemWriter<MAuthor> mAuthorMongoItemWriter = new MongoItemWriter<>();
        mAuthorMongoItemWriter.setTemplate(mongoOperations);

        return mAuthorMongoItemWriter;
    }

    @Bean
    public MongoItemWriter<MGenre> genreItemWriter() {
        MongoItemWriter<MGenre> mGenreMongoItemWriter = new MongoItemWriter<>();
        mGenreMongoItemWriter.setTemplate(mongoOperations);
        return mGenreMongoItemWriter;
    }

    @Bean
    public MongoItemWriter<MBook> bookItemWriter() {
        MongoItemWriter<MBook> mBookMongoItemWriter = new MongoItemWriter<>();
        mBookMongoItemWriter.setTemplate(mongoOperations);
        return mBookMongoItemWriter;
    }

    @Bean
    public ItemProcessor<Author, MAuthor> authorProcessor(AuthorConvertationService authorConvertationService) {
        return authorConvertationService::process;
    }

    @Bean
    public ItemProcessor<Genre, MGenre> genreProcessor(GenreComvertationService genreComvertationService) {
        return genreComvertationService::process;
    }

    @Bean
    public ItemProcessor<Book, MBook> bookProcessor(BookConvertationService convertationService) {
        return convertationService::process;
    }
}
