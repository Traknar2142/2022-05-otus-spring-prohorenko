package ru.otus.homework.service;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import ru.otus.homework.cash.IdCashService;
import ru.otus.homework.model.Author;
import ru.otus.homework.model.Genre;
import ru.otus.homework.model.MAuthor;
import ru.otus.homework.model.MGenre;

@Service
public class GenreComvertationService {
    private final IdCashService idCashService;

    public GenreComvertationService(IdCashService idCashService) {
        this.idCashService = idCashService;
    }

    public MGenre process(Genre genre){
        String key = genre.getClass().getName() +
                genre.getId().toString();

        String hexObjId = new ObjectId().toHexString();

        idCashService.put(key, hexObjId);

        MGenre mGenre  = new MGenre();
        mGenre.setId(hexObjId);
        mGenre.setName(genre.getName());
        return mGenre;
    }
}
