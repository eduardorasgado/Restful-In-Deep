package com.eduardocode.webservices.rest.restfulindeep.controller;

import com.eduardocode.webservices.rest.restfulindeep.exception.UserNotFoundException;
import com.eduardocode.webservices.rest.restfulindeep.model.Post;
import com.eduardocode.webservices.rest.restfulindeep.model.User;
import com.eduardocode.webservices.rest.restfulindeep.payload.ApiResponse;
import com.eduardocode.webservices.rest.restfulindeep.service.UserDaoService;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<List<User>> getAll() {
        List<User> users = this.userService.findAll();
        if(users != null) {
            return ResponseEntity.ok(users);
        }
        throw new RuntimeException("No se recuperó una lista con usuarios, esta es nula");
    }

    /**
     * Method to retreive a user given an id.
     * It throws a user not found exception if user does not exists, then it
     * returns a {@link com.eduardocode.webservices.rest.restfulindeep.exception.ResponseGeneralException}
     * class based json error details, using
     * {@link com.eduardocode.webservices.rest.restfulindeep.exception.CustomizedResponseEntityExceptionHandler}
     * rest controller and rest advice
     */
    @GetMapping("/{user_id}")
    public User getUserById(@PathVariable("user_id") Integer userId){
        User user = this.userService.findById(userId);
        if(user != null) {
            return user;
        }
        throw new UserNotFoundException("id: "+userId);
    }

    /**
     * Method to request the creation of a new user in db
     * @param user
     * @return
     */
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        User userCreated = this.userService.save(user);

        if(userCreated != null) {
            // api/users/{id}
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(userCreated.getId())
                    .toUri();

            return ResponseEntity.created(location)
                    .body(new ApiResponse(
                            "success",
                            "Se ha creado el usuario exitosamente"
                    ));
        }
        return new ResponseEntity<>(new ApiResponse(
                "error",
                "No pudo ser creado el usuario, un error de servidor se presentó," +
                        "intentelo más tarde"
        ), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Method to create a post given a owner user
     * @param userId
     * @param postRequest
     * @return
     */
    @PostMapping("/{user_id}/posts")
    public ResponseEntity<?> createPostByUser(
            @PathVariable("user_id") Integer userId,
            @RequestBody Post postRequest
    ) {
        if(postRequest != null) {
            Post post = this.userService.createPost(userId, postRequest);
            if(post != null) {
                return ResponseEntity.ok(new ApiResponse(
                        "success",
                        "Se ha creado un nuevo post con exito"
                ));
            }
            // en caso de que no exista el usuario
            throw new UserNotFoundException("id: "+userId);
        }
        // en caso de que postrequest venga vacio
        return new ResponseEntity<>(new ApiResponse(
                "error",
                "No existe el postRequest o está vacío"
        ), HttpStatus.BAD_REQUEST);
    }

    /**
     * Method to retreive all the posts given a user
     * @param userId
     * @return
     */
    @GetMapping("/{user_id}/posts")
    public List<Post> findAllPostByUser(@PathVariable("user_id") Integer userId) {
        return this.userService.findAllPostsByUserId(userId);
    }
}
