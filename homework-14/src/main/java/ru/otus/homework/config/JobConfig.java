package ru.otus.homework.config;

import org.bson.Document;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import ru.otus.homework.model.Book;
import ru.otus.homework.rowmapper.BookRowMapper;
import ru.otus.homework.service.BookConvertationService;

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

    private static final String QUERY =
            "SELECT t_book.id, title, author_id, genre_id, t_author.name as author_name, t_genre.name as genre_name " +
            "FROM t_book " +
            "LEFT JOIN t_author " +
            "ON t_book.author_id = t_author.id " +
            "LEFT JOIN t_genre " +
            "ON t_book.genre_id = t_genre.id ";

    private static final int CHUNK_SIZE = 1;

    @Bean
    public JdbcCursorItemReader<Book> h2Reader(DataSource dataSource) {
        JdbcCursorItemReader<Book> reader = new JdbcCursorItemReader<>();
        reader.setDataSource(dataSource);
        reader.setSql(QUERY);
        reader.setRowMapper(new BookRowMapper());
        return reader;
    }

    @Bean
    public MongoItemWriter<Document> mongoWriter(MongoTemplate mongoTemplate) {
        MongoItemWriter<Document> writer = new MongoItemWriter<>();
        writer.setCollection("books");
        writer.setTemplate(mongoTemplate);
        return writer;
    }

    @Bean
    public Job transferDataJob(Step transferDataStep) {
        return jobBuilderFactory.get("transferDataJob")
                .incrementer(new RunIdIncrementer())
                .flow(transferDataStep)
                .end()
                .build();
    }

    @Bean
    public ItemProcessor<Book, Document> processor(BookConvertationService convertationService) {
        return convertationService::convertToDoc;
    }

    @Bean
    public Step transferDataStep(ItemReader<Book> h2Reader,
                                 ItemProcessor<Book, Document> processor, ItemWriter<Document> mongoWriter) {
        return stepBuilderFactory.get("transferDataStep")
                .<Book, Document>chunk(CHUNK_SIZE)
                .reader(h2Reader)
                .processor(processor)
                .writer(mongoWriter)
                .build();
    }
}
