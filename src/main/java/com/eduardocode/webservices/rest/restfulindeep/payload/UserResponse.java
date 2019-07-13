package com.eduardocode.webservices.rest.restfulindeep.payload;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
 */
@AllArgsConstructor
@Data
@ApiModel(description = "Partial user data as response")
// second method to igore properties
//@JsonIgnoreProperties(value = {
//        "userId", "lastName"
//})
@JsonFilter("retreiveUser")
public class UserResponse extends ResourceSupport {

    @ApiModelProperty(notes = "${userResponse.model.userId.range}")
    private Integer userId;
    @ApiModelProperty(notes = "${userResponse.model.name.mayus}")
    private String name;
    @ApiModelProperty(notes = "${userResponse.model.name.mayus}")
    private String lastName;
    // first method to ignore properties when this object is returned to client side.
    //@JsonIgnore
    private Date birthDate;
}
