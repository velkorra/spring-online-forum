package org.example.onlineforum.controllers;

import jakarta.validation.Valid;
import org.example.onlineforum.dto.CommentCreateDto;
import org.example.onlineforum.dto.CommentUpdateDto;
import org.example.onlineforum.dto.NewCommentDto;
import org.example.onlineforum.dto.ThreadCommentDto;
import org.example.onlineforum.projections.ThreadCommentProjection;
import org.example.onlineforum.services.ThreadCommentService;
import org.forum.forumcontracts.controllers.ThreadCommentController;
import org.forum.forumcontracts.filters.ThreadCommentFilter;
import org.forum.forumcontracts.input.NewCommentForm;
import org.forum.forumcontracts.viewmodels.BaseViewModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping
public class ThreadCommentControllerImpl implements ThreadCommentController {
    private final ThreadCommentService threadCommentService;

    public ThreadCommentControllerImpl(ThreadCommentService threadCommentService) {
        this.threadCommentService = threadCommentService;
    }

    @GetMapping("thread/comment/new/{id}")
    public String commentCreateForm(@PathVariable String id, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("editState", true);
        redirectAttributes.addFlashAttribute("threadId", id);
        redirectAttributes.addFlashAttribute("newCommentForm", new NewCommentForm(""));
        return "redirect:/";
    }

    @PostMapping("thread/comment/new/{id}")
    public String commentCreate(@PathVariable String id, @Valid @ModelAttribute("newCommentForm") NewCommentForm form, BindingResult bindingResult, Principal principal, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("editState", true);
            redirectAttributes.addFlashAttribute("threadId", id);
            redirectAttributes.addFlashAttribute("newCommentForm", form);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.NewCommentForm", bindingResult);
            return "redirect:/";
        }
        threadCommentService.newComment(new NewCommentDto(form.content(), principal.getName(), id));
        return "redirect:/";
    }

    @Override
    @GetMapping("thread/comment/edit/{id}")
    public String commentEditForm(@PathVariable String id, RedirectAttributes redirectAttributes, Principal principal) {
        threadCommentService.checkCommentOwnershipOrThrow(id, principal.getName());

        ThreadCommentDto comment = threadCommentService.getCommentById(id);
        redirectAttributes.addFlashAttribute("editState", true);
        redirectAttributes.addFlashAttribute("commentId", id);
        redirectAttributes.addFlashAttribute("newCommentForm", new NewCommentForm(comment.content));
        return "redirect:/";
    }

    @Override
    @PostMapping("thread/comment/edit/{id}")
    public String commentEdit(@PathVariable String id, @Valid @ModelAttribute("newCommentForm") NewCommentForm form, BindingResult bindingResult, Principal principal, RedirectAttributes redirectAttributes) {
        threadCommentService.checkCommentOwnershipOrThrow(id, principal.getName());
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("editState", true);
            redirectAttributes.addFlashAttribute("commentId", id);
            redirectAttributes.addFlashAttribute("newCommentForm", form);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.NewCommentForm", bindingResult);
            return "redirect:/";
        }
        threadCommentService.updateComment(new CommentUpdateDto(id, form.content()));
        return "redirect:/";
    }

    @Override
    @GetMapping("thread/comment/delete/{id}")
    public String commentDelete(@PathVariable String id, RedirectAttributes redirectAttributes, Principal principal) {
        threadCommentService.checkCommentOwnershipOrThrow(id, principal.getName());
        threadCommentService.markDeleted(id);
        return "redirect:/";
    }

    @Override
    public BaseViewModel createBaseViewModel(String title) {
        return new BaseViewModel(title);
    }
}
