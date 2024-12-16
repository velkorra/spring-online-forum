package org.example.onlineforum.controllers.admin;

import jakarta.validation.Valid;
import org.example.onlineforum.dto.UserCreateDto;
import org.example.onlineforum.dto.UserUpdateDto;
import org.example.onlineforum.dto.mappers.UserMapper;
import org.forum.forumcontracts.filters.UserFilter;
import org.example.onlineforum.projections.UserProjection;
import org.example.onlineforum.services.UserService;
import org.forum.forumcontracts.controllers.admin.UserAdminController;
import org.forum.forumcontracts.input.UserCreateForm;
import org.forum.forumcontracts.input.UserUpdateForm;
import org.forum.forumcontracts.viewmodels.BaseViewModel;
import org.forum.forumcontracts.viewmodels.FullUserViewModel;
import org.forum.forumcontracts.viewmodels.HomeViewModel;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.util.Date;

@Controller
@RequestMapping("admin/users")
public class UserAdminControllerImpl implements UserAdminController {
    private final UserService userService;
    private final UserMapper userMapper;

    public UserAdminControllerImpl(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @Override
    @PutMapping
    public String updateUser(@Valid UserUpdateForm form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("model", new HomeViewModel(createBaseViewModel("Обновление пользователя")));
            model.addAttribute("form", form);
            return "admin/users/update";
        }
        userService.updateUser(new UserUpdateDto(form));
        return "redirect:/admin/users";
    }

    @Override
    @GetMapping("update/{id}")
    public String updateUserForm(@PathVariable String id, Model model) {
        var user = userService.getUserById(id);
        model.addAttribute("model", new HomeViewModel(createBaseViewModel("Обновление пользователя")));
        model.addAttribute("form", userMapper.toUpdateForm(user));
        return "admin/users/update";
    }

    @Override
    @DeleteMapping("{id}")
    public String deleteUser(@PathVariable String id, Model model) {
        userService.markDeleted(id);
        return "redirect:/admin/users";
    }


    @GetMapping
    public String listUsers(Model model, @ModelAttribute UserFilter userFilter, Pageable pageable) {
        model.addAttribute("model", new HomeViewModel(createBaseViewModel("Список пользователей")));
        var users = userService.getUsersWithCounts(userFilter, pageable);
        var userModels = users.map(userMapper::toViewModel);
        model.addAttribute("users", userModels);
        model.addAttribute("pageable", pageable);
        return "admin/users/list";
    }

    @Override
    @PostMapping
    public String createUser(@ModelAttribute("form") @Valid UserCreateForm userCreateForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("model", new HomeViewModel(createBaseViewModel("Создание пользователя")));
            model.addAttribute("form", userCreateForm);
            return "admin/users/create";
        }
        userService.createUser(userMapper.toDto(userCreateForm));
        return "redirect:/admin/users";
    }

    @Override
    @GetMapping("/create")
    public String createUserForm(Model model) {
        model.addAttribute("model", new HomeViewModel(createBaseViewModel("Создание пользователя")));
        model.addAttribute("form", UserCreateForm.blank());
        return "/admin/users/create";
    }

    @Override
    public BaseViewModel createBaseViewModel(String title) {
        return new BaseViewModel(title);
    }


}
