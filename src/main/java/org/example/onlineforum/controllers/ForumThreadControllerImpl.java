package org.example.onlineforum.controllers;

import jakarta.validation.Valid;
import org.example.onlineforum.dto.mappers.ForumThreadMapper;
import org.example.onlineforum.projections.ThreadProjection;
import org.example.onlineforum.services.CategoryService;
import org.example.onlineforum.services.ForumThreadService;
import org.forum.forumcontracts.controllers.ForumThreadController;
import org.forum.forumcontracts.input.NewThreadForm;
import org.forum.forumcontracts.input.ThreadEditForm;
import org.forum.forumcontracts.viewmodels.BaseViewModel;
import org.forum.forumcontracts.viewmodels.HomeViewModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping
public class ForumThreadControllerImpl implements ForumThreadController {
    private final ForumThreadService threadService;
    private final CategoryService categoryService;
    private final ForumThreadMapper forumThreadMapper;

    public ForumThreadControllerImpl(ForumThreadService threadService, CategoryService categoryService, ForumThreadMapper forumThreadMapper) {
        this.threadService = threadService;
        this.categoryService = categoryService;
        this.forumThreadMapper = forumThreadMapper;
    }

    @Override
    @PostMapping("/thread/new")
    public String createThread(@ModelAttribute("form") @Valid NewThreadForm form, BindingResult bindingResult, Principal principal, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("model", new HomeViewModel(createBaseViewModel("Новая тема")));
            model.addAttribute("form", form);
            model.addAttribute("categories", categoryService.getAll());
            return "new-thread";
        }
        threadService.createThread(principal.getName(), forumThreadMapper.toDto(form));
        return "redirect:/";
    }

    @Override
    @GetMapping("/thread/new")
    public String newThread(Model model) {
        model.addAttribute("model", new HomeViewModel(createBaseViewModel("Новая тема")));
        model.addAttribute("form", NewThreadForm.blank());
        model.addAttribute("categories", categoryService.getAll());
        return "new-thread";
    }

    @Override
    @GetMapping("thread/edit/{id}")
    public String editThread(@PathVariable String id, Model model, Principal principal) {
        threadService.checkThreadOwnershipOrThrow(id, principal.getName());
        ThreadProjection thread = threadService.getThreadById(id);
        model.addAttribute("model", new HomeViewModel(createBaseViewModel("Редактирование темы")));
        model.addAttribute("form", new ThreadEditForm(id, thread.getTitle(), thread.getContent()));
        return "edit-thread";
    }

    @Override
    @PostMapping("thread/edit/{id}")
    public String editThreadForm(@PathVariable String id, @Valid ThreadEditForm form, BindingResult bindingResult, Model model, Principal principal) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("model", new HomeViewModel(createBaseViewModel("Редактирование темы")));
            model.addAttribute("form", form);
            return "edit-thread";
        }
        threadService.checkThreadOwnershipOrThrow(id, principal.getName());
        threadService.updateThread(forumThreadMapper.toDto(form));
        return "redirect:/";
    }

    @Override
    @GetMapping("/thread/delete/{id}")
    public String deleteThread(@PathVariable String id, Model model, Principal principal){
        threadService.checkThreadOwnershipOrThrow(id, principal.getName());
        threadService.markDeleted(id);
        return "redirect:/";
    }

    @Override
    public BaseViewModel createBaseViewModel(String title) {
        return new BaseViewModel(title);
    }


}
