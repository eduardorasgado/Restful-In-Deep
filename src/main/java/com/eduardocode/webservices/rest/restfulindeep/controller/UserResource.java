package com.eduardocode.webservices.rest.restfulindeep.controller;

import com.eduardocode.webservices.rest.restfulindeep.model.User;
import com.eduardocode.webservices.rest.restfulindeep.service.UserDaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        User userCreated = this.userService.save(user);

        // /users/{id}
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(userCreated.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
