package com.eduardocode.webservices.rest.restfulindeep.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.Set;

/**
 * <h1>PostRequest</h1>
 * Class to be able to map a request body in case user wants to create a new post
 *
 * @author Eduardo Rasgado Ruiz
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    private Set<String> tags;
}
