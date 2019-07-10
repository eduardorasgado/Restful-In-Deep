package com.eduardocode.webservices.rest.restfulindeep.controller;

import com.eduardocode.webservices.rest.restfulindeep.payload.UserResponse;
import com.google.common.collect.Sets;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * <h1>UserFilteringController</h1>
 * Class that improves static data filtering
 *
 * @author Eduardo Rasgado Ruiz
 * @since 1.0
 */
@RestController
@RequestMapping("/api/partial-user")
public class UserFilteringController {

    @GetMapping
    public Set<UserResponse> retreiveAllPartial() {
        return Sets.newHashSet(
                new UserResponse(1, "value2", "value3", null),
                new UserResponse(2, "value22", "value32", null),
                new UserResponse(3, "value23", "value33", null),
                new UserResponse(4, "value24", "value34", null)
        );
    }

    @GetMapping("/{user_id}")
    public UserResponse retreiveSomeUser(
            @PathVariable("user_id") Integer userId
    ) {

        return new UserResponse(userId, "value2", "value3", null);
    }
}
