package org.example.onlineforum.services.implementations;

import jakarta.transaction.Transactional;
import org.example.onlineforum.config.RestPage;
import org.example.onlineforum.dto.CommentCreateDto;
import org.example.onlineforum.dto.CommentUpdateDto;
import org.example.onlineforum.dto.NewCommentDto;
import org.example.onlineforum.dto.ThreadCommentDto;
import org.example.onlineforum.entities.ForumThread;
import org.example.onlineforum.entities.ThreadComment;
import org.example.onlineforum.entities.User;
import org.example.onlineforum.exceptions.CommentNotFoundException;
import org.example.onlineforum.exceptions.ThreadNotFoundException;
import org.example.onlineforum.exceptions.UserNotFoundException;
import org.example.onlineforum.projections.dto.ThreadCommentProjectionDto;
import org.example.onlineforum.repositories.search.CommentSearchRepository;
import org.example.onlineforum.repositories.ForumThreadRepository;
import org.example.onlineforum.repositories.ThreadCommentRepository;
import org.example.onlineforum.repositories.UserRepository;
import org.example.onlineforum.services.ThreadCommentService;
import org.forum.forumcontracts.filters.ThreadCommentFilter;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
@EnableCaching
public class ThreadCommentServiceImpl implements ThreadCommentService {
    private final ThreadCommentRepository commentRepository;
    private final ForumThreadRepository threadRepository;
    private final UserRepository userRepository;
    private final CommentSearchRepository commentSearchRepository;

    public ThreadCommentServiceImpl(ThreadCommentRepository commentRepository, ForumThreadRepository threadRepository, UserRepository userRepository, CommentSearchRepository commentSearchRepository) {
        this.commentRepository = commentRepository;
        this.threadRepository = threadRepository;
        this.userRepository = userRepository;
        this.commentSearchRepository = commentSearchRepository;
    }

    @Override
    @Transactional
    @CacheEvict(value = {"comments", "threads"}, allEntries = true)
    public void createComment(CommentCreateDto commentCreateDto) {
        User author = userRepository.findById(commentCreateDto.authorId())
                .orElseThrow(() -> new UserNotFoundException("User with id " + commentCreateDto.authorId() + " not found"));
        ForumThread thread = threadRepository.findById(commentCreateDto.threadId())
                .orElseThrow(() -> new ThreadNotFoundException("Thread with id " + commentCreateDto.threadId() + " not found"));
        if (commentCreateDto.parentCommentId() != null && !commentCreateDto.parentCommentId().isEmpty()) {
            ThreadComment parent = commentRepository.findById(commentCreateDto.parentCommentId())
                    .orElseThrow(() -> new CommentNotFoundException("Comment with id " + commentCreateDto.parentCommentId() + " not found"));
            ThreadComment newComment = new ThreadComment(author, commentCreateDto.content(), thread, parent);
            commentRepository.create(newComment);
            return;
        }
        ThreadComment newComment = new ThreadComment(author, commentCreateDto.content(), thread);
        commentRepository.create(newComment);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"comments", "threads"}, allEntries = true)
    public void newComment(NewCommentDto newCommentDto) {
        User user = userRepository.findByUsername(newCommentDto.authorUsername()).orElseThrow(
                () -> new ThreadNotFoundException("Thread with username " + newCommentDto.authorUsername() + " not found"));
        ForumThread thread = threadRepository.findById(newCommentDto.threadId())
                .orElseThrow(() -> new ThreadNotFoundException("Thread with id " + newCommentDto.threadId() + " not found"));
        ThreadComment newComment = new ThreadComment(user, newCommentDto.content(), thread);
        commentRepository.create(newComment);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"comments", "threads"}, allEntries = true)
    public void updateComment(CommentUpdateDto commentUpdateDto) {
        ThreadComment existingComment = commentRepository.findById(commentUpdateDto.id())
                .orElseThrow(() -> new CommentNotFoundException("Comment with id " + commentUpdateDto.id() + " not found"));
        existingComment.setContent(commentUpdateDto.content());
        commentRepository.update(existingComment);
    }

    @Override
    public ThreadCommentDto getCommentById(String commentId) {
        return commentRepository.findById(commentId)
                .map(ThreadCommentDto::new)
                .orElseThrow(() -> new CommentNotFoundException("Comment with id " + commentId + " not found"));
    }

    @Override
    public void checkCommentOwnershipOrThrow(String threadId, String username) {
        ThreadComment comment = commentRepository.findById(threadId).orElseThrow(
                () -> new ThreadNotFoundException("Thread with id " + threadId + " not found")
        );
        if (!comment.getAuthor().getUsername().equals(username)) {
            throw new AccessDeniedException("You doesn't have rights to edit this resource");
        }
    }
    @Override
    @Cacheable("comments")
    public Page<ThreadCommentProjectionDto> searchComments(ThreadCommentFilter filter, Pageable pageable) {
        return new RestPage<>(commentSearchRepository.searchThreadComments(filter, pageable).map(ThreadCommentProjectionDto::new));
    }

    @Override
    @Transactional
    @CacheEvict(value = {"comments", "threads"}, allEntries = true)
    public void markDeleted(String id) {
        ThreadComment comment = commentRepository.findById(id).orElseThrow(
                () -> new ThreadNotFoundException("Thread with id " + id + " not found"));
        comment.setDeleted(true);
        commentRepository.update(comment);
    }
}
