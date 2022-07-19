package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.exceptions.EntityNotFoundException;
import ru.otus.homework.repository.BookRepository;
import ru.otus.homework.repository.CommentRepository;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

/**
 * @author Прохоренко Виктор
 */
@Service
public class CommentServiceImpl implements CommentService {
    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;
    private final CommentRenderService commentOutRenderService;

    public CommentServiceImpl(BookRepository bookRepository, CommentRepository commentRepository, CommentRenderService commentOutRenderService) {
        this.bookRepository = bookRepository;
        this.commentRepository = commentRepository;
        this.commentOutRenderService = commentOutRenderService;
    }

    @Transactional(readOnly = true)
    @Override
    public void printCommentsByBooksId(Long bookId) {
        List<Comment> commentsByBookId = getCommentsByBookId(bookId);
        commentOutRenderService.printFormatMessage(commentsByBookId);
    }

    @Transactional
    @Override
    public Comment addComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Comment> getCommentsByBookId(Long bookId) {
        return commentRepository.findCommentByBookId(bookId);
    }

    @Transactional(readOnly = true)
    @Override
    public Comment getCommentById(Long id) {
        return commentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(MessageFormat.format("Комментарий с id {0} не найден", id)));
    }

    @Transactional
    @Override
    public Comment updateComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Transactional
    @Override
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
