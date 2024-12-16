package org.example.onlineforum.controllers;

import org.example.onlineforum.dto.mappers.CommentMapper;
import org.example.onlineforum.dto.mappers.ForumThreadMapper;
import org.example.onlineforum.dto.mappers.UserMapper;
import org.example.onlineforum.projections.ThreadCommentProjection;
import org.example.onlineforum.projections.ThreadProjection;
import org.example.onlineforum.services.ThreadCommentService;
import org.example.onlineforum.services.ForumThreadService;
import org.forum.forumcontracts.filters.ForumThreadFilter;
import org.forum.forumcontracts.filters.ThreadCommentFilter;
import org.forum.forumcontracts.filters.UserFilter;
import org.example.onlineforum.projections.UserProjection;
import org.example.onlineforum.services.UserService;
import org.forum.forumcontracts.viewmodels.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("page")
public class PageController {
    private final UserService userService;
    private final ForumThreadService threadService;
    private final ThreadCommentService commentService;
    private final UserMapper userMapper;
    private final CommentMapper commentMapper;
    private final ForumThreadMapper threadMapper;

    public PageController(UserService userService, ForumThreadService threadService, ThreadCommentService commentService, UserMapper userMapper, CommentMapper commentMapper, ForumThreadMapper threadMapper) {
        this.userService = userService;
        this.threadService = threadService;
        this.commentService = commentService;
        this.userMapper = userMapper;
        this.commentMapper = commentMapper;
        this.threadMapper = threadMapper;
    }

    @GetMapping
    public Page<FullUserViewModel> page(@ModelAttribute UserFilter userFilter, Pageable pageable) {
        return userService.getUsersWithCounts(userFilter, pageable).map(userMapper::toViewModel);
    }

    @GetMapping("counts")
    public Page<UserProjection> userWithCounts(@ModelAttribute UserFilter userFilter, Pageable pageable) {
        return userService.getUsersWithCounts(userFilter, pageable);
    }


    @GetMapping("threads")
    public Page<ThreadProjection> searchThreads(ForumThreadFilter filter, Pageable pageable){
        return threadService.searchThreads(filter, pageable);
    }

    @GetMapping("comments")
    public Page<ThreadCommentProjection> searchComments(ThreadCommentFilter filter, Pageable pageable){
        return commentService.searchComments(filter, pageable);
    }

    @GetMapping("home")
    public MainPageViewModel home(Pageable pageable) {
        Pageable page = PageRequest.of(0, 5, Sort.by("createdAt").descending());

        List<ForumThreadWithCommentsViewModel> threadModels = new ArrayList<>();
        var threads = threadService.searchThreads(ForumThreadFilter.blank(), page);
        for (var thread : threads) {
            var filter = ThreadCommentFilter.blank();
            var comments = commentService.searchComments(filter,
                            PageRequest.of(0, 5, Sort.by("createdAt")))
                    .map(commentMapper::toViewModel);
            threadModels.add(threadMapper.toViewModel(thread, comments.getContent()));
        }
        return new MainPageViewModel(new BaseViewModel("aa"), threadModels, new ArrayList<>());
    }

}
