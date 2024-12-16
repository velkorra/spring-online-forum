package org.example.onlineforum.controllers.admin;

import jakarta.validation.Valid;
import org.example.onlineforum.dto.CommentCreateDto;
import org.example.onlineforum.dto.CommentUpdateDto;
import org.example.onlineforum.dto.mappers.CommentMapper;
import org.example.onlineforum.services.ThreadCommentService;
import org.forum.forumcontracts.controllers.admin.CommentAdminController;
import org.forum.forumcontracts.filters.ThreadCommentFilter;
import org.forum.forumcontracts.input.ThreadCommentCreateForm;
import org.forum.forumcontracts.input.ThreadCommentUpdateForm;
import org.forum.forumcontracts.viewmodels.BaseViewModel;
import org.forum.forumcontracts.viewmodels.HomeViewModel;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/comments")
public class CommentAdminControllerImpl implements CommentAdminController {
    private final ThreadCommentService commentService;
    private final CommentMapper commentMapper;

    public CommentAdminControllerImpl(ThreadCommentService commentService, CommentMapper commentMapper) {
        this.commentService = commentService;
        this.commentMapper = commentMapper;
    }

    @Override
    @GetMapping
    public String listComments(@ModelAttribute ThreadCommentFilter filter, Pageable pageable, Model model) {
        model.addAttribute("model", new HomeViewModel(createBaseViewModel("Список комментариев")));
        var models = commentService.searchComments(filter, pageable)
                .map(commentMapper::toViewModel);
        model.addAttribute("comments", models);
        model.addAttribute("pageable", pageable);
        return "admin/comments/list";
    }

    @Override
    @PostMapping
    public String createComment(@ModelAttribute("form") @Valid ThreadCommentCreateForm threadCommentCreateForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("model", new HomeViewModel(createBaseViewModel("Создание комментария")));
            model.addAttribute("form", threadCommentCreateForm);
            return "admin/comments/create";
        }
        commentService.createComment(new CommentCreateDto(threadCommentCreateForm));
        return "redirect:/admin/comments";
    }

    @Override
    @GetMapping("/create")
    public String createCommentForm(Model model) {
        model.addAttribute("model", new HomeViewModel(createBaseViewModel("Создание комментария")));
        model.addAttribute("form", new ThreadCommentCreateForm(null, null, null, null));
        return "admin/comments/create";
    }

    @Override
    @PutMapping
    public String updateComment(@ModelAttribute("form") @Valid ThreadCommentUpdateForm form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()){
            model.addAttribute("model", new HomeViewModel(createBaseViewModel("Редактирование комментария")));
            model.addAttribute("form", form);
            return "admin/comments/update";
        }
        commentService.updateComment(new CommentUpdateDto(form));
        return "redirect:/admin/comments";
    }

    @Override
    @GetMapping("update/{id}")
    public String updateCommentForm(@PathVariable String id, Model model) {
        var comment = commentService.getCommentById(id);
        model.addAttribute("model", new HomeViewModel(createBaseViewModel("Редактирование комментария")));
        model.addAttribute("form", new ThreadCommentUpdateForm(id, comment.content));
        return "admin/comments/update";
    }

    @Override
    public String deleteComment(String id, Model model) {
        return "";
    }

    @Override
    public BaseViewModel createBaseViewModel(String title) {
        return new BaseViewModel(title);
    }

}
