package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.domain.Comment;

import java.util.List;

/**
 * @author Прохоренко Виктор
 */
@Service
public class CommentOutputRenderServiceImpl implements CommentRenderService {
    private final MessageDialogService messageDialogService;

    public CommentOutputRenderServiceImpl(MessageDialogService messageDialogService) {
        this.messageDialogService = messageDialogService;
    }

    @Override
    public void printFormatMessage(List<Comment> comments) {
        messageDialogService.outputMessage(formatListToStringForOutput(comments));
    }

    private String formatListToStringForOutput(List<Comment> comments) {
        StringBuilder builder = new StringBuilder();
        for (Comment comment : comments){
            builder.append(comment.getCommentMessage()).append("\n");
        }
        return builder.toString();
    }
}
