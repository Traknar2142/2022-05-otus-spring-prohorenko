package ru.otus.service;

import org.springframework.core.io.Resource;

import java.util.Locale;

/**
 * @author Прохоренко Виктор
 */
public interface ResourceProvider {
    Resource getResourceDependsOfLocale();
}
