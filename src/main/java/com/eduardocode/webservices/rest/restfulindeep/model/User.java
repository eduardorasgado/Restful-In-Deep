package com.eduardocode.webservices.rest.restfulindeep.model;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.ResourceSupport;

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
@ApiModel(description = "User data stored in db")
public class User extends ResourceSupport {
    private Integer userId;
    @NotBlank
    private String name;
    @NotBlank
    private String lastName;
    @Past
    private Date birthDate;
    private List<Post> posts;

    public User(Integer userId, String name, String lastName, Date birthDate) {
        this.userId = userId;
        this.name = name;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }
}
