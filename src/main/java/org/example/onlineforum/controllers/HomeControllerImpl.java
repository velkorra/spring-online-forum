package org.example.onlineforum.controllers;

import org.example.onlineforum.dto.mappers.CommentMapper;
import org.example.onlineforum.dto.mappers.ForumThreadMapper;
import org.example.onlineforum.services.CategoryService;
import org.example.onlineforum.services.ThreadCommentService;
import org.example.onlineforum.services.ForumThreadService;
import org.forum.forumcontracts.controllers.HomeController;
import org.forum.forumcontracts.filters.ForumThreadFilter;
import org.forum.forumcontracts.filters.ThreadCommentFilter;
import org.forum.forumcontracts.viewmodels.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class HomeControllerImpl implements HomeController {
    private final ForumThreadService threadService;
    private final ThreadCommentService commentService;
    private final CategoryService categoryService;
    private final ForumThreadMapper forumThreadMapper;
    private final CommentMapper commentMapper;

    public HomeControllerImpl(ForumThreadService threadService, ThreadCommentService commentService, CategoryService categoryService, ForumThreadMapper forumThreadMapper, CommentMapper commentMapper) {
        this.threadService = threadService;
        this.commentService = commentService;
        this.categoryService = categoryService;
        this.forumThreadMapper = forumThreadMapper;
        this.commentMapper = commentMapper;
    }

    @Override
    @GetMapping("/{category}")
    public String homeWithCategory(Model model, Pageable pageable, @PathVariable String category, Principal principal) {
        return home(model, pageable, category, principal);
    }

    @Override
    @GetMapping
    public String homeWithoutCategory(Model model, Pageable pageable, Principal principal) {
        return home(model, pageable, null, principal);
    }

    private String home(Model model, Pageable pageable, String category, Principal principal) {
        if (principal != null) model.addAttribute("username", principal.getName());
        Pageable page = PageRequest.of(0, 5, Sort.by("createdAt").descending());
        var filter = ForumThreadFilter.blank();
        if (category != null) filter = filter.withCategory(category);
        List<ForumThreadWithCommentsViewModel> threadModels = new ArrayList<>();
        var threads = threadService.searchThreads(filter, page);
        for (var thread : threads) {
            var commentFilter = ThreadCommentFilter.blank().withTreadId(thread.getId());
            var comments = commentService.searchComments(commentFilter,
                            PageRequest.of(0, 5, Sort.by("createdAt")))
                    .map(commentMapper::toViewModel);
            threadModels.add(forumThreadMapper.toViewModel(thread, comments.getContent()));
        }
        model.addAttribute("model", new MainPageViewModel(createBaseViewModel("Главная страница"), threadModels, categoryService.getAll().stream().map(c -> new CategoryViewModel(c.getName())).toList()));
        return "index";
    }



    @Override
    public BaseViewModel createBaseViewModel(String title) {
        return new BaseViewModel(title);
    }
}
