package com.eduardocode.webservices.rest.restfulindeep.payload;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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
@ApiModel
public class PostRequest {
    @NotBlank
    @Size(min = 10, max = 40)
    private String title;
    @NotBlank
    @Size(min = 20, max = 500)
    private String content;
    private Set<String> tags;
}
