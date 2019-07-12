package com.eduardocode.webservices.rest.restfulindeep.controller;

import com.eduardocode.webservices.rest.restfulindeep.model.User;
import com.eduardocode.webservices.rest.restfulindeep.payload.PostResponse;
import com.eduardocode.webservices.rest.restfulindeep.payload.UserResponse;
import com.google.common.collect.Sets;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <h1>UserFilteringController</h1>
 * Class that improves static data filtering
 *
 * @author Eduardo Rasgado Ruiz
 * @since 1.0
 */
@RestController
@RequestMapping("/api/partial-users")
public class UserFilteringController {

    /**
     * Method to return all partial user data to client.
     *
     * @return
     *      A set of UserResponse that inherits from {@link org.springframework.hateoas.ResourceSupport}
     *      and have partial data for every member of the set.
     */
    @GetMapping
    public Set<UserResponse> retreiveAllPartial() {
        Set<UserResponse> users = Sets.newHashSet(
                new UserResponse(1, "value2", "value3", null),
                new UserResponse(2, "value22", "value32", null),
                new UserResponse(3, "value23", "value33", null),
                new UserResponse(4, "value24", "value34", null)
        );

        users.forEach((UserResponse user) -> {
            ControllerLinkBuilder selfLink = ControllerLinkBuilder.linkTo(
                    ControllerLinkBuilder.methodOn(
                            this.getClass()
                    ).retreiveSomeUser(user.getUserId())
            );
            if(user.getLinks().size() <=0) {
                user.add(selfLink.withSelfRel());
            }
        });

        return users;
    }

    /**
     * Method to return specific partial user data given a user_id in path variable(client url).
     *
     * @param userId
     * @return
     *      User Resource with partial data
     */
    @GetMapping("/{user_id}")
    public Resource<UserResponse> retreiveSomeUser(
            @PathVariable("user_id") Integer userId
    ) {
        UserResponse user = new UserResponse(userId, "value2", "value3", null);
        Resource<UserResponse> resource = new Resource<>(user);
        ControllerLinkBuilder link = ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(
                        this.getClass()
                ).retreiveSomeUser(userId)
        );
        resource.add(link.withSelfRel());
        return resource;
    }

    /**
     * Method that returns a specific post resource given a user id and the post id
     * @param userId
     *      User Id, should be integer
     * @param postId
     *      Post Id, should be Integer
     * @return
     *      A User resource partial data
     */
    @GetMapping("/{user_id}/posts/{post_id}")
    public Resource<PostResponse> getPostUser(
            @PathVariable("user_id") Integer userId,
            @PathVariable("post_id") Integer postId
    ) {
        PostResponse post = new PostResponse(postId,
                "title1",
                "content1",
                new HashSet<>());

        Resource<PostResponse> resource = new Resource<>(post);

        ControllerLinkBuilder link = ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(
                        this.getClass()
                ).getPostUser(userId, postId)
        );
        resource.add(link.withSelfRel());

        return resource;
    }

    @GetMapping("/{user_id}/posts")
    public ResponseEntity<List<PostResponse>> getAllPostsByUser(
            @PathVariable("user_id") Integer userId
    ) {
        List<PostResponse> posts = Arrays.asList(
                new PostResponse(1, "title", "content", new HashSet<>()),
                new PostResponse(2, "title2", "content", new HashSet<>()),
                new PostResponse(3, "title3", "content", new HashSet<>()),
                new PostResponse(4, "title4", "content", new HashSet<>())
        );

        posts.forEach((PostResponse post) -> {
            ControllerLinkBuilder selfLink = ControllerLinkBuilder.linkTo(
                    ControllerLinkBuilder.methodOn(
                            this.getClass()
                    ).getPostUser(userId, post.getPostId())
            );
            post.add(selfLink.withSelfRel());
        });
        return ResponseEntity.ok(posts);
    }
}
