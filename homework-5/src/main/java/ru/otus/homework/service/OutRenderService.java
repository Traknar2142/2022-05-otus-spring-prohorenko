package ru.otus.homework.service;

import java.util.List;

/**
 * @author Прохоренко Виктор
 */
public interface OutRenderService<S> {
    void printFormatMessage(List<S> list);
}
