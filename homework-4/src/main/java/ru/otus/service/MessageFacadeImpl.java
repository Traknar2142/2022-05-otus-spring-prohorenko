package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Прохоренко Виктор
 */
@Service
@RequiredArgsConstructor
public class MessageFacadeImpl implements MessageFacade {
    private final MessageDialogService messageDialogService;
    private final LocalizationMessageService localizationMessageService;

    @Override
    public String inputMessage() {
        return messageDialogService.inputMessage();
    }

    @Override
    public String outputMessage(String message) {
        return messageDialogService.outputMessage(message);
    }

    @Override
    public String outputLocalizedMessage(String message) {
        String localizationMessage = getLocalizationMessage(message);
        return messageDialogService.outputMessage(localizationMessage);
    }

    @Override
    public String getLocalizationMessage(String message) {
        return localizationMessageService.getLocalizationMessage(message);
    }
}
