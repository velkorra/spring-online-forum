package org.example.onlineforum.controllers.admin;

import org.example.onlineforum.dto.ThreadCreateDro;
import org.example.onlineforum.dto.mappers.ForumThreadMapper;
import org.example.onlineforum.services.CategoryService;
import org.example.onlineforum.services.ForumThreadService;
import org.forum.forumcontracts.controllers.admin.ThreadAdminController;
import org.forum.forumcontracts.filters.ForumThreadFilter;
import org.forum.forumcontracts.input.ThreadCreateFrom;
import org.forum.forumcontracts.input.ThreadUpdateForm;
import org.forum.forumcontracts.viewmodels.BaseViewModel;
import org.forum.forumcontracts.viewmodels.HomeViewModel;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("admin/threads")
public class ThreadAdminControllerImpl implements ThreadAdminController {
    private final ForumThreadService threadService;
    private final CategoryService categoryService;
    private final ForumThreadMapper forumThreadMapper;

    public ThreadAdminControllerImpl(ForumThreadService threadService, CategoryService categoryService, ForumThreadMapper forumThreadMapper) {
        this.threadService = threadService;
        this.categoryService = categoryService;
        this.forumThreadMapper = forumThreadMapper;
    }

    @Override
    @GetMapping
    public String listThreads(@ModelAttribute ForumThreadFilter threadFilter, Pageable pageable, Model model) {
        var threadModels = threadService.searchThreads(threadFilter, pageable).map(forumThreadMapper::toViewModel);
        model.addAttribute("model", new HomeViewModel(createBaseViewModel("Список тем")));
        model.addAttribute("pageable", pageable);
        model.addAttribute("threads", threadModels);
        return "admin/threads/list";
    }

    @Override
    @PostMapping
    public String createThread(@ModelAttribute("form") ThreadCreateFrom threadCreateFrom, BindingResult bindingResult, Model model) {
        model.addAttribute("model", new HomeViewModel(createBaseViewModel("Создание темы")));
        if (bindingResult.hasErrors()) {
            model.addAttribute("form", threadCreateFrom);
            model.addAttribute("categories", categoryService.getAll());
            return "admin/threads/create";
        }
        threadService.createThread(new ThreadCreateDro(threadCreateFrom));
        return "redirect:/admin/threads";
    }

    @Override
    @GetMapping("/create")
    public String createThreadForm(Model model) {
        model.addAttribute("model", new HomeViewModel(createBaseViewModel("Создание темы")));
        model.addAttribute("form", new ThreadCreateFrom("", "", "", ""));
        model.addAttribute("categories", categoryService.getAll());
        return "admin/threads/create";
    }

    @Override
    @PutMapping
    public String updateThread(@ModelAttribute("form") ThreadUpdateForm form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("model", new HomeViewModel(createBaseViewModel("Изменение темы")));
            model.addAttribute("form", form);
            return "admin/threads/update";
        }
        threadService.updateThread(forumThreadMapper.toDto(form));
        return "redirect:/admin/threads";
    }

    @Override
    @GetMapping("/update/{id}")
    public String updateThreadForm(@PathVariable String id, Model model) {
        var thread = threadService.getThreadById(id);
        model.addAttribute("model", new HomeViewModel(createBaseViewModel("Изменение темы")));
        model.addAttribute("form", forumThreadMapper.toUpdateForm(thread));
        return "admin/threads/update";
    }

    @Override
    public String deleteThread(String id, Model model) {
        return "";
    }

    @Override
    public BaseViewModel createBaseViewModel(String title) {
        return new BaseViewModel(title);
    }
}
