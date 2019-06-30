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
            // adding url to every user
            for(User u : users) {
                ControllerLinkBuilder link = ControllerLinkBuilder.linkTo(
                        ControllerLinkBuilder.methodOn(
                                this.getClass()
                        ).getUserById(u.getUserId())
                );
                // avoiding reinsert another element into user links
                if(u.getLinks().size() <= 0) {
                    u.add(link.withRel("url"));
                }
            }

            return ResponseEntity.ok(users);
        }
        throw new RuntimeException("No se recuperó una lista con usuarios, esta es nula");
    }

    /**
     * Method to retreive a user given an postId.
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

            List<Post> posts = this.everyMemberPostListSelfUrl(userId);
            user.setPosts(posts);

            // Using HATEOAS
            Resource<User> resource = new Resource<>(user);

            // adding a link to resource: getAll() in this controller
            ControllerLinkBuilder linkToGetAll = ControllerLinkBuilder.linkTo(
                    ControllerLinkBuilder.methodOn(
                            this.getClass()
                    ).getAll());

            ControllerLinkBuilder autoLink = this.getUserLink(userId);

            resource.add(linkToGetAll.withRel("all-users"));
            resource.add(autoLink.withRel("url"));

            return resource;
        }
        throw new UserNotFoundException("Usuario no existente, postId: "+userId);
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
                // api/users/{postId}
                URI location = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{postId}")
                        .buildAndExpand(userCreated.getUserId())
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
        throw new UserNotFoundException("El usuario que trata de eliminar no existe, postId: "+userId);
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
            throw new UserNotFoundException("usuario no existente, postId: "+userId);
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
        if(this.userService.userExists(userId)) {
            List<Post> posts = this.everyMemberPostListSelfUrl(userId);
            return new ResponseEntity<>(posts, HttpStatus.OK);
        }
        throw new UserNotFoundException("El usuario de los repositorios a los que quiere tener" +
                "acceso, no existe, id: "+userId);
    }

    /**
     * This method returns a post given a user
     * @param userId
     * @param postId
     * @return
     */
    @GetMapping("/{user_id}/posts/{post_id}")
    public ResponseEntity<Resource<Post>> getUserPost(
            @PathVariable("user_id") Integer userId,
            @PathVariable("post_id") Integer postId
    ){
        if(this.userService.userExists(userId)) {
            Post post = this.userService.findPostByUserIdAndByPostId(userId, postId);
            if(post != null) {

                Resource<Post> postResource = new Resource<>(post);

                // getting the user profile link owns this post
                ControllerLinkBuilder userLink = this.getUserLink(userId);

                // adding self url
                ControllerLinkBuilder selfLink = ControllerLinkBuilder.linkTo(
                        ControllerLinkBuilder.methodOn(
                                this.getClass()
                        ).getUserPost(userId, postId)
                );

                postResource.add(userLink.withRel("user_profile"));
                postResource.add(selfLink.withRel("url"));

                return ResponseEntity.ok(postResource);
            }
            else {
                throw new PostNotFoundException("Post de usuario con postId:"+userId
                        +" inexistente, post postId: "+postId);
            }
        }
        throw new UserNotFoundException("Usuario inexistente, postId: "+ userId);
    }

    // UTILITIES =========

    /**
     * Method that add a self link to every post inside a user's list of posts
     * @param userId
     * @return
     */
    private List<Post> everyMemberPostListSelfUrl(Integer userId) {
        List<Post> posts = this.userService.findAllPostsByUserId(userId);

        for(Post p : posts) {
            ControllerLinkBuilder link = ControllerLinkBuilder.linkTo(
                    ControllerLinkBuilder.methodOn(
                            this.getClass()
                    ).getUserPost(userId, p.getPostId())
            );
            // Esto porque temporalmente se está trabajando en una lista, por tanto para no
            // modificar mas veces la lista se comprueba si es la primera vez que se agrega
            // una url
            if(p.getLinks().size() <= 0) {
                p.add(link.withRel("url"));
            }
        }
        return posts;
    }

    /**
     * Method to get a link of user profile
     * @param userId
     * @return
     */
    private ControllerLinkBuilder getUserLink(Integer userId) {
        // adding a link to self user
        ControllerLinkBuilder autoLink = ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(
                        this.getClass()
                ).getUserById(userId)
        );
        return autoLink;
    }

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
