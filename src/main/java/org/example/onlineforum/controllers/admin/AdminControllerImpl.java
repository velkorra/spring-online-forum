package org.example.onlineforum.controllers.admin;

import org.forum.forumcontracts.controllers.admin.AdminHomeController;
import org.forum.forumcontracts.viewmodels.BaseViewModel;
import org.forum.forumcontracts.viewmodels.HomeViewModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminControllerImpl implements AdminHomeController{
    @Override
    @GetMapping
    public String adminPanel(Model model) {
        var viewModel = new HomeViewModel(createBaseViewModel("Admin Panel"));
        model.addAttribute("model", viewModel);
        return "admin/admin";
    }

    public BaseViewModel createBaseViewModel(String title) {
        return new BaseViewModel(title);
    }
}
