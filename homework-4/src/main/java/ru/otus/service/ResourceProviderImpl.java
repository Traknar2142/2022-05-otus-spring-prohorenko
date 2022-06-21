package ru.otus.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * @author Прохоренко Виктор
 */
@Service
public class ResourceProviderImpl implements ResourceProvider{
    private final String resourcePathWithoutLocale;

    public ResourceProviderImpl(@Value("#{'${file-name}'}")String resourcePathWithoutLocal) {
        this.resourcePathWithoutLocale = resourcePathWithoutLocal;
    }

    @Override
    public Resource getResourceDependsOfLocale() {
        Locale locale = LocaleContextHolder.getLocale();
        String resourcePathWithLocale = getResourceNameDependsOfLocale(locale);
        return new ClassPathResource(resourcePathWithLocale);
    }

    private String getResourceNameDependsOfLocale(Locale locale){
        return resourcePathWithoutLocale + "_" + locale + ".csv";
    }
}
