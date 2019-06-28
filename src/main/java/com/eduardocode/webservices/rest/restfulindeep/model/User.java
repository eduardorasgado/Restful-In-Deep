package com.eduardocode.webservices.rest.restfulindeep.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.util.Date;
import java.util.List;

/**
 * <h1>User</h1>
 * User model representation for user in database
 *
 * @author Eduardo Rasgado Ruiz
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer id;
    @NotBlank
    private String name;
    @NotBlank
    private String lastName;
    @Past
    private Date birthDate;
    private List<Post> posts;

    public User(Integer id, String name, String lastName, Date birthDate) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }
}
