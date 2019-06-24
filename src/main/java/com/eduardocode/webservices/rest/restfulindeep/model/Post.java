package com.eduardocode.webservices.rest.restfulindeep.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class Post {
    private Integer id;
    private String title;
    private String content;
    private Set<String> tags;
    private Date timestamp;
}
