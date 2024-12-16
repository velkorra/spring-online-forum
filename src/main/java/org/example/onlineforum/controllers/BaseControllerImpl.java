package org.example.onlineforum.controllers;

import org.forum.forumcontracts.controllers.BaseController;
import org.forum.forumcontracts.viewmodels.BaseViewModel;

public class BaseControllerImpl implements BaseController {
    @Override
    public BaseViewModel createBaseViewModel(String title) {
        return new BaseViewModel(title);
    }
}
