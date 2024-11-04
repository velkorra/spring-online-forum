package org.example.onlineforum;

import jakarta.transaction.Transactional;
import org.example.onlineforum.entities.*;
import org.example.onlineforum.entities.enums.Role;
import org.example.onlineforum.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ForumThreadRepository threadRepository;
    private final ThreadCommentRepository commentRepository;
    private final ReactionRepository reactionRepository;
    private final PinnedThreadsRepository pinnedThreadsRepository;

    public DataInitializer(UserRepository userRepository,
                           CategoryRepository categoryRepository,
                           ForumThreadRepository threadRepository,
                           ThreadCommentRepository commentRepository,
                           ReactionRepository reactionRepository,
                           PinnedThreadsRepository pinnedThreadsRepository) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.threadRepository = threadRepository;
        this.commentRepository = commentRepository;
        this.reactionRepository = reactionRepository;
        this.pinnedThreadsRepository = pinnedThreadsRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        User admin = new User(
                "admin",
                "admin123",
                "admin@forum.com",
                "Administrator",
                Role.ADMIN
        );

        User user1 = new User(
                "john_doe",
                "pass123",
                "john@example.com",
                "John Doe",
                Role.USER
        );

        User user2 = new User(
                "jane_doe",
                "pass456",
                "jane@example.com",
                "Jane Doe",
                Role.USER
        );

        List<User> users = userRepository.createAll(Arrays.asList(admin, user1, user2));

        Category programming = new Category("Programming");
        Category gaming = new Category("Gaming");
        Category music = new Category("Music");

        List<Category> categories = categoryRepository.createAll(
                Arrays.asList(programming, gaming, music)
        );

        ForumThread thread1 = new ForumThread(
                user1,
                "Java vs Python",
                "Which one is better for beginners?",
                programming
        );
        thread1.setViewsCount(150);

        ForumThread thread2 = new ForumThread(
                user2,
                "Best gaming laptops 2024",
                "Looking for recommendations",
                gaming
        );
        thread2.setViewsCount(300);

        List<ForumThread> threads = threadRepository.createAll(Arrays.asList(thread1, thread2));

        ThreadComment comment1 = new ThreadComment(
                admin,
                "Python is more beginner-friendly",
                thread1
        );
        comment1.setCreatedAt(LocalDateTime.now());

        ThreadComment comment2 = new ThreadComment(
                user2,
                "Java has better job prospects",
                thread1
        );

        ThreadComment reply1 = new ThreadComment(
                user1,
                "That's a good point!",
                thread1
        );
        reply1.setParentComment(comment1);

        List<ThreadComment> comments = commentRepository.createAll(
                Arrays.asList(comment1, comment2, reply1)
        );

        Reaction reaction1 = new Reaction(user1, comment1, true);
        Reaction reaction2 = new Reaction(user2, thread1, true);
        Reaction reaction3 = new Reaction(admin, comment2, false);

        System.out.println(reaction1.getId());
        List<Reaction> reactions = reactionRepository.createAll(
                Arrays.asList(reaction1, reaction2, reaction3)
        );
        System.out.println(reaction1.getId());

        PinnedThreads pinned1 = new PinnedThreads(thread1, user1);
        PinnedThreads pinned2 = new PinnedThreads(thread2, user2);
        comment1.setContent("NOOOOO");
//        commentRepository.update(comment1);

        comment1.setContent("YEEEES");
//        var user = userRepository.findByEmail("john@example.com").orElse(null);
//
//        user.setEmail("FSDfunndasiufdsf");
        List<PinnedThreads> pinnedThreads = pinnedThreadsRepository.createAll(
                Arrays.asList(pinned1, pinned2)
        );

        System.out.println("База данных заполнена тестовыми данными:");
        System.out.println("Создано пользователей: " + users.size());
        System.out.println("Создано категорий: " + categories.size());
        System.out.println("Создано тредов: " + threads.size());
        System.out.println("Создано комментариев: " + comments.size());
        System.out.println("Создано реакций: " + reactions.size());
        System.out.println("Создано закрепленных тредов: " + pinnedThreads.size());
    }
}