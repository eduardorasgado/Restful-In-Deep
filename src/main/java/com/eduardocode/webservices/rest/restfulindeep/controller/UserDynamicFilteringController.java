package com.eduardocode.webservices.rest.restfulindeep.controller;

import com.eduardocode.webservices.rest.restfulindeep.payload.UserResponse;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

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

}