package com.eduardocode.webservices.rest.restfulindeep.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.ResourceSupport;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.util.Date;
import java.util.Set;

/**
 * <h1>Post</h1>
 * Class that represents a post, it is owned by a user
 *
 * @author Eduardo Rasgado Ruiz
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post extends ResourceSupport {
    private Integer postId;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    private Set<String> tags;
    private Date timestamp;

}
