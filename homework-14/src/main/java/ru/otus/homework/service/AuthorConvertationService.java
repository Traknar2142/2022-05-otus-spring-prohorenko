package ru.otus.homework.service;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import ru.otus.homework.cash.IdCashService;
import ru.otus.homework.model.Author;
import ru.otus.homework.model.MAuthor;

@Service
public class AuthorConvertationService {
    private final IdCashService idCashService;

    public AuthorConvertationService(IdCashService idCashService) {
        this.idCashService = idCashService;
    }

    public MAuthor process(Author author){
        String key = author.getClass().getName() +
                author.getId().toString();

        String hexObjId = new ObjectId().toHexString();

        idCashService.put(key, hexObjId);

        MAuthor mAuthor = new MAuthor();
        mAuthor.setId(hexObjId);
        mAuthor.setName(author.getName());
        return mAuthor;
    }
}
