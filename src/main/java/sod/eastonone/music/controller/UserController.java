package sod.eastonone.music.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import sod.eastonone.music.auth.models.User;

@Controller
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @QueryMapping
    public User getUserInfo(@AuthenticationPrincipal User user) {
    	logger.debug("Entering/Exiting getUserInfo for user " + user.getId());
        return user;
    }
}