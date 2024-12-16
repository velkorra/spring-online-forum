package org.example.onlineforum.services.implementations;

import jakarta.transaction.Transactional;
import org.example.onlineforum.config.RestPage;
import org.example.onlineforum.dto.NewThreadDto;
import org.example.onlineforum.dto.ThreadCreateDro;
import org.example.onlineforum.dto.ThreadDto;
import org.example.onlineforum.dto.ThreadUpdateDto;
import org.example.onlineforum.entities.Category;
import org.example.onlineforum.entities.ForumThread;
import org.example.onlineforum.entities.User;
import org.example.onlineforum.exceptions.CategoryNotFoundException;
import org.example.onlineforum.exceptions.ThreadNotFoundException;
import org.example.onlineforum.exceptions.UserNotFoundException;
import org.example.onlineforum.projections.ThreadProjection;
import org.example.onlineforum.projections.dto.ThreadProjectionDto;
import org.example.onlineforum.repositories.CategoryRepository;
import org.example.onlineforum.repositories.ForumThreadRepository;
import org.example.onlineforum.repositories.search.ThreadSearchRepository;
import org.example.onlineforum.repositories.UserRepository;
import org.example.onlineforum.services.ForumThreadService;
import org.forum.forumcontracts.filters.ForumThreadFilter;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@EnableCaching
public class ThreadServiceImpl implements ForumThreadService {
    private final ForumThreadRepository threadRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final ThreadSearchRepository threadSearchRepository;

    public ThreadServiceImpl(ForumThreadRepository threadRepository, CategoryRepository categoryRepository, UserRepository userRepository, ThreadSearchRepository threadSearchRepository) {
        this.threadRepository = threadRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.threadSearchRepository = threadSearchRepository;
    }

    @Override
    public List<ThreadDto> getAllThreads() {
        return List.of();
    }

    @Override
    public ThreadProjection getThreadById(String id) {
        return threadSearchRepository.getProjectionById(id).orElseThrow(
                () -> new ThreadNotFoundException("Thread with id " + id + "does not exist")
        );
    }

    @Override
    @Transactional
    @CacheEvict(value = "threads", allEntries = true)
    public void createThread(ThreadCreateDro thread) {
        Category category = categoryRepository.findById(thread.category()).orElseThrow(
                () -> new CategoryNotFoundException(thread.category())
        );
        User user = userRepository.findById(thread.authorId())
                .orElseThrow(() -> new UserNotFoundException("User with id " + thread.authorId() + " not found"));
        ForumThread newThread = new ForumThread(user, thread.title(), thread.content(), category);
        threadRepository.create(newThread);
    }

    @Override
    @Transactional
    @CacheEvict(value = "threads", allEntries = true)
    public void createThread(String username, NewThreadDto thread) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundException("User with username " + username + " not found")
        );
        Category category = categoryRepository.findById(thread.category()).orElseThrow(
                () -> new CategoryNotFoundException(thread.category())
        );
        ForumThread newThread = new ForumThread(user, thread.title(), thread.content(), category);
        threadRepository.create(newThread);
    }

    @Override
    @Transactional
    @CacheEvict(value = "threads", allEntries = true)
    public void updateThread(ThreadUpdateDto thread) {
        ForumThread existingThread = threadRepository.findById(thread.id())
                .orElseThrow(() -> new ThreadNotFoundException("Thread with id " + thread.id() + " not found"));
        if (thread.category() != null) {
        Category category = categoryRepository.findById(thread.category()).orElseThrow(
                () -> new CategoryNotFoundException(thread.category())
        );
            existingThread.setCategory(category);
        }
        existingThread.setTitle(thread.title());
        existingThread.setContent(thread.content());
        threadRepository.update(existingThread);
    }

    @Override
    @Cacheable("threads")
    public Page<ThreadProjectionDto> searchThreads(ForumThreadFilter filter, Pageable pageable) {
        return new RestPage<>(threadSearchRepository.searchForumThreads(filter, pageable).map(ThreadProjectionDto::new));
    }

    @Override
    public void checkThreadOwnershipOrThrow(String threadId, String username) {
        ForumThread forumThread = threadRepository.findById(threadId).orElseThrow(
                () -> new ThreadNotFoundException("Thread with id " + threadId + " not found")
        );
        if (!forumThread.getAuthor().getUsername().equals(username)) {
            throw new AccessDeniedException("You doesn't have rights to edit this resource");
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = {"comments", "threads"}, allEntries = true)
    public void markDeleted(String id) {
        ForumThread forumThread = threadRepository.findById(id).orElseThrow(
                () -> new ThreadNotFoundException("Thread with id " + id + " not found")
        );
        forumThread.setDeleted(true);
        threadRepository.update(forumThread);
    }
}
