package ru.otus.service;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * @author Прохоренко Виктор
 */
@Service
public class LocalizationMessageServiceImpl implements LocalizationMessageService{
    private final MessageSource messageSource;

    public LocalizationMessageServiceImpl(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public String getLocalizationMessage(String message) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(message, null, locale);
    }
}
