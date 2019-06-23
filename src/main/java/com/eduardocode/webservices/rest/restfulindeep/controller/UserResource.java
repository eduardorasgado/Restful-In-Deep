package com.eduardocode.webservices.rest.restfulindeep.controller;

import com.eduardocode.webservices.rest.restfulindeep.model.User;
import com.eduardocode.webservices.rest.restfulindeep.service.UserDaoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <h1>UserResource</h1>
 * Class that represents the user resource in our application. It develops basic
 * interaction with user dao service
 *
 * @author Eduardo Rasgado Ruiz
 */
@RestController
@RequestMapping("/api/users")
public class UserResource {

    private UserDaoService userService;

    public UserResource(UserDaoService userService) {
        this.userService = userService;
    }
    /**
     * Method to retreive all users
     */
    @GetMapping
    public List<User> getAll() {
        return userService.findAll();
    }

    /**
     * Method to retreive a user given an id
     */
    @GetMapping("/{user_id}")
    public User getUserById(@PathVariable("user_id") Integer userId){
        return this.userService.findById(userId);
    }
}
