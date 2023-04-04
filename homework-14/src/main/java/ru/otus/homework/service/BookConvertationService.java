package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.cash.IdCashService;
import ru.otus.homework.model.Book;
import ru.otus.homework.model.MAuthor;
import ru.otus.homework.model.MBook;
import ru.otus.homework.model.MGenre;

@Service
public class BookConvertationService {
    private final IdCashService idCashService;

    public BookConvertationService(IdCashService idCashService) {
        this.idCashService = idCashService;
    }

    public MBook process(Book book) {
        MBook mBook = new MBook();
        mBook.setTitle(book.getTitle());
        //bookDoc.put("title", book.getTitle());

        MAuthor mAuthor = new MAuthor();

        String key = book.getAuthor().getClass().getName() +
                book.getAuthor().getId().toString();

        mAuthor.setId(idCashService.pop(key));
        mAuthor.setName(book.getAuthor().getName());
        //author.put("_id", new ObjectId());
        //author.put("name", book.getAuthor().getName());

        MGenre mGenre = new MGenre();

        key = book.getGenre().getClass().getName() +
                book.getGenre().getId().toString();

        mGenre.setId(idCashService.pop(key));
        mGenre.setName(book.getGenre().getName());
        //genre.put("_id", new ObjectId());
        //genre.put("name", book.getGenre().getName());


        //bookDoc.put("author", author);
        //bookDoc.put("genre", genre);

        mBook.setAuthor(mAuthor);
        mBook.setGenre(mGenre);
        return mBook;
    }
}

