package org.example.onlineforum.controllers;

import jakarta.validation.Valid;
import org.example.onlineforum.dto.mappers.UserMapper;
import org.example.onlineforum.exceptions.EmailAlreadyExistsException;
import org.example.onlineforum.exceptions.UsernameAlreadyExistsException;
import org.example.onlineforum.services.AuthService;
import org.forum.forumcontracts.controllers.AuthController;
import org.forum.forumcontracts.input.UserLoginForm;
import org.forum.forumcontracts.input.UserRegistrationForm;
import org.forum.forumcontracts.viewmodels.BaseViewModel;
import org.forum.forumcontracts.viewmodels.HomeViewModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping
public class AuthControllerImpl implements AuthController {
    private final AuthService authService;
    private final UserMapper userMapper;

    public AuthControllerImpl(AuthService authService, UserMapper userMapper) {
        this.authService = authService;
        this.userMapper = userMapper;
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("form", new UserLoginForm(null, null));
        model.addAttribute("model", new HomeViewModel(createBaseViewModel("Логин")));
        return "login";
    }


    @Override
    @PostMapping("login/error")
    public String onFailedLogin(String emailOrUsername, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute(emailOrUsername);
        redirectAttributes.addFlashAttribute("badCredentials", true);
        return "redirect:/login";
    }

    @Override
    @GetMapping("/register")
    public String registrationForm(Model model) {
        model.addAttribute("model", new HomeViewModel(createBaseViewModel("Регистрация")));
        model.addAttribute("form", UserRegistrationForm.blank());
        return "register";
    }

    @Override
    @PostMapping("/register")
    public String register(@ModelAttribute("form") @Valid UserRegistrationForm form, BindingResult bindingResult, Model model) {
        if (!bindingResult.hasErrors()) {
            try {
                authService.register(userMapper.toDto(form));
                return "redirect:/login";
            } catch (EmailAlreadyExistsException exception) {
                model.addAttribute("emailTaken", true);
            } catch (UsernameAlreadyExistsException exception) {
                model.addAttribute("usernameTaken", true);
            }
        }
        model.addAttribute("model", new HomeViewModel(createBaseViewModel("Регистрация")));
        model.addAttribute("form", form);
        return "register";
    }


    @Override
    public BaseViewModel createBaseViewModel(String title) {
        return new BaseViewModel(title);
    }
}
