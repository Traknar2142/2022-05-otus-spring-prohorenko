package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.exceptions.EntityNotFoundException;
import ru.otus.homework.repository.CommentRepository;

import java.text.MessageFormat;
import java.util.List;

/**
 * @author Прохоренко Виктор
 */
@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final CommentRenderService commentOutRenderService;

    public CommentServiceImpl(CommentRepository commentRepository, CommentRenderService commentOutRenderService) {
        this.commentRepository = commentRepository;
        this.commentOutRenderService = commentOutRenderService;
    }

    @Override
    public void printCommentsByBooksId(String bookId) {
        List<Comment> commentsByBookId = getCommentsByBookId(bookId);
        commentOutRenderService.printFormatMessage(commentsByBookId);
    }

    @Override
    public Comment addComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> getCommentsByBookId(String bookId) {
        return commentRepository.findCommentByBookId(bookId);
    }

    @Override
    public Comment getCommentById(String id) {
        return commentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(MessageFormat.format("Комментарий с id {0} не найден", id)));
    }

    @Override
    public Comment updateComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(String id) {
        commentRepository.deleteById(id);
    }
}
