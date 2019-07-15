package com.eduardocode.webservices.rest.restfulindeep.v2.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * This class represents a complete name for a user response payload
 *
 * @author Eduardo Rasgado Ruiz
 * @version 1
 * @see UserResponse
 */
@AllArgsConstructor
@Data
public class Name {
    private String firstName;
    private String lastName;
}
