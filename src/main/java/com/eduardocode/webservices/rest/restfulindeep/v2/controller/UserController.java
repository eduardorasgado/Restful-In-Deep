package com.eduardocode.webservices.rest.restfulindeep.v2.controller;

import com.eduardocode.webservices.rest.restfulindeep.v2.payload.Name;
import com.eduardocode.webservices.rest.restfulindeep.v2.payload.UserResponse;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.google.common.collect.Sets;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

/**
 * <h1>UserController</h1>
 * Class that represents the interaction between user and the second version of user api
 * resources.
 *
 * In v1 we can find it as {@link com.eduardocode.webservices.rest.restfulindeep.controller.UserDynamicFilteringController}
 *
 * @author Eduardo Rasgado Ruiz
 * @version 2.
 */
@RestController
@RequestMapping("/api/dynamic-partial-users")
public class UserController {

    /**
     * Method to return all the users given the real required user fields client wants.
     * It implements a get mapping annotation for the 2nd version of api, using a
     * produces Accept header versioning method.
     * @param userId
     * @param name
     * @param birthDate
     * @return
     */
    @GetMapping(
            // TODO: Reseach about how to create documentation for different api versions.
            produces = "application/vnd.eduardocode.restfulindeep.v2+json"
    )
    public MappingJacksonValue retreiveUsersList(
            @RequestParam("userId") Boolean userId,
            @RequestParam("name") Boolean name,
            @RequestParam("birthDate") Boolean birthDate
    ){
        Set<String> params = new HashSet<>();
        if(userId) {
            params.add("userId");
        }
        if(name) {
            params.add("name");
        }
        if(birthDate) {
            params.add("birthDate");
        }

        Set<UserResponse> users = Sets.newHashSet(
                new UserResponse(1, new Name("billy", "bob"), null),
                new UserResponse(2, new Name("Domo", "Dim"), null),
                new UserResponse(3, new Name("Dima", "Domm"), null),
                new UserResponse(4, new Name("Timmy", "Turner"), null)
        );

        MappingJacksonValue mapping = new MappingJacksonValue(users);
        FilterProvider filters = new SimpleFilterProvider()
                .addFilter(
                        "retreiveUser",
                            SimpleBeanPropertyFilter.filterOutAllExcept(
                                    params
                            )
                        );
        mapping.setFilters(filters);

        return mapping;
    }

}
