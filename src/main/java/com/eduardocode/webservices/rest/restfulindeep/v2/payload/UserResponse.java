package com.eduardocode.webservices.rest.restfulindeep.v2.payload;


import com.fasterxml.jackson.annotation.JsonFilter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
 * @version 2
 */
@AllArgsConstructor
@Data
@ApiModel(description = "Partial user data as response")
@JsonFilter("retreiveUser")
public class UserResponse extends ResourceSupport {

    @ApiModelProperty(notes = "${userResponse.model.userId.range}")
    private Integer userId;
    @ApiModelProperty(notes = "${userResponse.model.name.mayus}")
    private Name name;
    // first method to ignore properties when this object is returned to client side.
    //@JsonIgnore
    private Date birthDate;
}