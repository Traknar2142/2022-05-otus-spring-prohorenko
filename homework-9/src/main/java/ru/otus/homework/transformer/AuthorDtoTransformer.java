package ru.otus.homework.transformer;

import lombok.experimental.UtilityClass;
import ru.otus.homework.domain.Author;
import ru.otus.homework.dto.AuthorDto;

/**
 * @author Прохоренко Виктор
 */
@UtilityClass
public class AuthorDtoTransformer {
    public static AuthorDto toDto(Author author){
        AuthorDto authorDto = new AuthorDto();
        authorDto.setId(author.getId());
        authorDto.setName(authorDto.getName());
        return authorDto;
    }
}
