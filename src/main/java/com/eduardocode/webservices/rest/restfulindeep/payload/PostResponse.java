package com.eduardocode.webservices.rest.restfulindeep.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.hateoas.ResourceSupport;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * <h1>PostResponse</h1>
 * Partial post data  that will be returned to client side as response.
 *
 * @author Eduardo Rasgado Ruiz
 */
@AllArgsConstructor
@Data
@ApiModel(description = "Partial Post data delivered to client side, every post is owned by a user")
// ignoring some properties before returning them to client side
@JsonIgnoreProperties(value = {
        "postId",
        "tags"
})
public class PostResponse extends ResourceSupport {
    private Integer postId;
    @ApiModelProperty(notes = "${postResponse.model.title.size}")
    private String title;
    @ApiModelProperty(notes = "${postResponse.model.content.size}")
    private String content;
    private Set<String> tags;
}
