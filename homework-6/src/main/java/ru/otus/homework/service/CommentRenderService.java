package ru.otus.homework.service;

import ru.otus.homework.domain.Comment;

import java.util.List;

/**
 * @author Прохоренко Виктор
 */
public interface CommentRenderService {
    void printFormatMessage(List<Comment> list);
}
