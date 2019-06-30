package com.eduardocode.webservices.rest.restfulindeep.controller;

import com.eduardocode.webservices.rest.restfulindeep.exception.PostNotFoundException;
import com.eduardocode.webservices.rest.restfulindeep.exception.UserNotFoundException;
import com.eduardocode.webservices.rest.restfulindeep.model.Post;
import com.eduardocode.webservices.rest.restfulindeep.model.User;
import com.eduardocode.webservices.rest.restfulindeep.payload.ApiResponse;
import com.eduardocode.webservices.rest.restfulindeep.payload.PostRequest;
import com.eduardocode.webservices.rest.restfulindeep.payload.UserSignUpRequest;
import com.eduardocode.webservices.rest.restfulindeep.service.UserDaoService;
import com.eduardocode.webservices.rest.restfulindeep.util.BasicUtils;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Date;
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
    public Resource<User> getUserById(@PathVariable("user_id") Integer userId){
        User user = this.userService.findById(userId);
        if(user != null) {
            // Using HATEOAS
            Resource<User> resource = new Resource<>(user);

            // adding a link to resource: getAll() in this controller
            ControllerLinkBuilder linkToGetAll = ControllerLinkBuilder.linkTo(
                    ControllerLinkBuilder.methodOn(
                            this.getClass()
                    ).getAll());

            ControllerLinkBuilder autoLink = ControllerLinkBuilder.linkTo(
                    ControllerLinkBuilder.methodOn(
                            this.getClass()
                    ).getUserById(userId)
            );

            resource.add(linkToGetAll.withRel("all-users"));
            resource.add(autoLink.withRel("url"));
            return resource;
        }
        throw new UserNotFoundException("Usuario no existente, id: "+userId);
    }

    /**
     * Method to request the creation of a new user in db
     * @param userReq
     * @return
     */
    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody UserSignUpRequest userReq) {

        if(userReq != null) {
            User user = this.mappingUserReq(userReq);
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
        }

        return new ResponseEntity<>(new ApiResponse(
                "error",
                "No pudo ser creado el usuario, un error de servidor se presentó," +
                        "intentelo más tarde"
        ), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Method to delete an user from
     * @param userId
     * @return
     */
    @DeleteMapping("/{user_id}")
    public ResponseEntity<?> deleteUserById(
            @PathVariable("user_id") Integer userId
    ) {
        if(this.userService.userExists(userId)) {
            User user = this.userService.deleteById(userId);

            StringBuilder message = new StringBuilder("El usuario: ");
            message.append(user.getName());
            message.append(" ha sido eliminado con éxito");

            return new ResponseEntity<>(new ApiResponse(
                    "success",
                            message.toString()
            ), HttpStatus.OK);
        }
        throw new UserNotFoundException("El usuario que trata de eliminar no existe, id: "+userId);
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
            @Valid @RequestBody PostRequest postRequest
    ) {
        if(postRequest != null) {
            Post post = this.mappingPostPayload(postRequest);
            post.setTimestamp(new Date());
            post = this.userService.createPost(userId, post);
            if(post != null) {
                return ResponseEntity.ok(new ApiResponse(
                        "success",
                        "Se ha creado un nuevo post con exito"
                ));
            }
            // en caso de que no exista el usuario
            throw new UserNotFoundException("usuario no existente, id: "+userId);
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
    public ResponseEntity<List<Post>> findAllPostByUser(@PathVariable("user_id") Integer userId) {
        return new ResponseEntity<>(this.userService.findAllPostsByUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/{user_id}/posts/{post_id}")
    public ResponseEntity<Post> getUserPost(
            @PathVariable("user_id") Integer userId,
            @PathVariable("post_id") Integer postId
    ){
        if(this.userService.userExists(userId)) {
            Post post = this.userService.findPostByUserIdAndByPostId(userId, postId);
            if(post != null) {
                return ResponseEntity.ok(post);
            }
            else {
                throw new PostNotFoundException("Post de usuario con id:"+userId
                        +" inexistente, post id: "+postId);
            }
        }
        throw new UserNotFoundException("Usuario inexistente, id: "+ userId);
    }

    // UTILITIES =========

    private User mappingUserReq(UserSignUpRequest userReq) {
        User user = new User();
        user.setName(userReq.getName());
        user.setLastName(userReq.getLastName());
        Date date = BasicUtils.transformToDate(userReq.getBirthDate());
        if(date != null) {
            user.setBirthDate(date);
        } else {
            user.setBirthDate(new Date());
        }
        return user;
    }

    private Post mappingPostPayload(PostRequest postRequest) {
        Post post = new Post();
        post.setTitle(postRequest.getTitle());
        post.setContent(postRequest.getContent());
        post.setTags(postRequest.getTags());

        return post;
    }

}
