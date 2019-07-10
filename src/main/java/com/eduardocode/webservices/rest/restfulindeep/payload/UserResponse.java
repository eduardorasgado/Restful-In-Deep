package com.eduardocode.webservices.rest.restfulindeep.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.hateoas.ResourceSupport;

import java.util.Date;

/**
 * <h1>UserResponse</h1>
 * User data to send to client, this will be partially sent because
 * jsonIgnore annotation
 *
 * @author Eduardo Rasgado Ruiz
 */
@AllArgsConstructor
@Data
public class UserResponse extends ResourceSupport {
    private Integer userId;
    private String name;
    private String lastName;
    @JsonIgnore
    private Date birthDate;
}
