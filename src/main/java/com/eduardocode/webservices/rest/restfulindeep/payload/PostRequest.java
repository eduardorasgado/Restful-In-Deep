package com.eduardocode.webservices.rest.restfulindeep.payload;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(description = "Post data that comes from client, to create new post in db")
public class PostRequest {
    @ApiModelProperty(notes = "${error.postRequest.title.size}", required = true)
    @NotBlank
    @Size(min = 10, max = 40)
    private String title;
    @ApiModelProperty(notes = "${error.postRequest.content.size}", required = true)
    @NotBlank
    @Size(min = 20, max = 500)
    private String content;
    @ApiModelProperty(notes = "${postRequest.model.tags.element.singleword}")
    private Set<String> tags;
}
