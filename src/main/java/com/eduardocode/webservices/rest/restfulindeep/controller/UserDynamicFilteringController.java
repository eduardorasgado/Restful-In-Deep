package com.eduardocode.webservices.rest.restfulindeep.controller;

import com.eduardocode.webservices.rest.restfulindeep.payload.UserResponse;
import com.fasterxml.jackson.databind.ser.FilterProvider;
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
@RequestMapping("/dynamic-partial-users")
public class UserDynamicFilteringController {

    /**
     * Method to return user with userId and name
     *
     * @param userId
     * @return
     */
    @GetMapping("/{user_id}/filter1")
    public UserResponse retreiveUser(
            @PathVariable("user_id") Integer userId
    ) {
        UserResponse user = new UserResponse(1, "name", "lastname", new Date());
        MappingJacksonValue mapping = new MappingJacksonValue(user);
        FilterProvider filterProvider = new SimpleFilterProvider()
                .addFilter("userIdFilter",
                        SimpleBeanPropertyFilter.filterOutAllExcept(
                                "lastName",
                                "birthDate"
                        ));
        return user;
    }

    /**
     * Method to return name and lastname
     *
     * @param userId
     * @return
     */
    @GetMapping("/{user_id}/filter2")
    public UserResponse retreiveUser2(
            @PathVariable("user_id") Integer userId
    ) {
        UserResponse user = new UserResponse(1, "name", "lastname", new Date());
        return user;
    }

}