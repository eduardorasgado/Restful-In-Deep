package com.eduardocode.webservices.rest.restfulindeep.controller;

import com.eduardocode.webservices.rest.restfulindeep.payload.UserResponse;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.google.common.collect.Sets;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * <h1>UserDynamicFilteringController</h1>
 * Class to represent a request and responses related to dynamic
 * user and posts filtering
 *
 * @author Eduardo Rasgado Ruiz
 */
@RestController
@RequestMapping("/api/dynamic-partial-users")
public class UserDynamicFilteringController {

    /**
     * Method to return user with userId and name using a dynamic filter
     *
     * @param userId
     * @return
     */
    @GetMapping("/{user_id}/filter1")
    public MappingJacksonValue retreiveUser(
            @PathVariable("user_id") Integer userId
    ) {
        UserResponse user = new UserResponse(userId, "name", "lastname", new Date());

        // object that will contain all the filters will be applied to user
        MappingJacksonValue mapping = new MappingJacksonValue(user);

        // filter will register in string type all the fields from UserResponse
        // class that will be excepted from filter at the response.
        FilterProvider filterProvider = new SimpleFilterProvider()
                /**
                 * note: filter id should be register at {@link UserResponse}
                 * using a JsonFilter annotation, for getting the mapping effect
                 */
                .addFilter("retreiveUser",
                        // so these fields will be keep in the response
                        SimpleBeanPropertyFilter.filterOutAllExcept(
                                "userId",
                                "name"
                        ));
        mapping.setFilters(filterProvider);
        return mapping;
    }

    /**
     * Method to return name and lastname as result of dynamic filtering
     *
     * @param userId
     * @return
     */
    @GetMapping("/{user_id}/filter2")
    public MappingJacksonValue retreiveUser2(
            @PathVariable("user_id") Integer userId
    ) {
        UserResponse user = new UserResponse(userId, "name", "lastname", new Date());

        MappingJacksonValue mapping = new MappingJacksonValue(user);

        FilterProvider filters = new SimpleFilterProvider()
                // we can user a filter id from a class annotated with
                // jsonifilter with no problem
                .addFilter("retreiveUser", SimpleBeanPropertyFilter.filterOutAllExcept(
                        "name",
                        "lastName"
                ));
        mapping.setFilters(filters);

        return mapping;
    }

    /**
     * Method to return all the users with certain fields using a dynamic
     * filter
     * @param userId
     *      value in url, boolean
     * @param name
     *      value in url, boolean
     * @param lastName
     *      value in url, boolean
     * @param birthDate
     *      value in url, boolean
     * @return
     *      dynamic filtered out user list
     */
    @GetMapping
    public MappingJacksonValue retreiveUsersList(
            @RequestParam("userId") Boolean userId,
            @RequestParam("name") Boolean name,
            @RequestParam("lastName") Boolean lastName,
            @RequestParam("birthDate") Boolean birthDate
    ){
        Set<String> params = new HashSet<>();
        if(userId) {
            params.add("userId");
        }
        if(name) {
            params.add("name");
        }
        if(lastName) {
            params.add("lastName");
        }
        if(birthDate) {
            params.add("birthDate");
        }

        Set<UserResponse> users = Sets.newHashSet(
                new UserResponse(1, "value2", "value3", null),
                new UserResponse(2, "value22", "value32", null),
                new UserResponse(3, "value23", "value33", null),
                new UserResponse(4, "value24", "value34", null)
        );

        MappingJacksonValue mapping = new MappingJacksonValue(users);

        FilterProvider filters = new SimpleFilterProvider()
                .addFilter(
                        "retreiveUser",
                        SimpleBeanPropertyFilter
                                .filterOutAllExcept(
                                        params
                                )
                );
        mapping.setFilters(filters);
        return mapping;
    }
}