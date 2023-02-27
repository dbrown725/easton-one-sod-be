package sod.eastonone.music.controller;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import sod.eastonone.music.auth.models.User;

@Controller
public class UserController {

    @QueryMapping
    public User getUserInfo(@AuthenticationPrincipal User user) {
        return user;
    }
}