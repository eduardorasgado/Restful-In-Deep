package com.eduardocode.webservices.rest.restfulindeep.payload;

import com.eduardocode.webservices.rest.restfulindeep.config.SwaggerConfiguration;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * <h1>UserSignUpRequest</h1>
 * Class to be able to map user data from signup request in user resource
 *
 * @author Eduardo Rasgado Ruiz
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
// for swagger documentation
@ApiModel(description="All details about the new user")
public class UserSignUpRequest {
    @NotBlank
    // TODO: Separate value in application.properties, parse it and used in messages
    /**
     * api model property works herewith ${} taking message in mensajes.property
     * because of @properySource annotation at class level
     * in {@link com.eduardocode.webservices.rest.restfulindeep.config.SwaggerConfiguration}
     * info:
     *  https://www.vojtechruzicka.com/documenting-spring-boot-rest-api-swagger-springfox/
     */
    @ApiModelProperty(notes = "${error.userSignUpRequest.name.size}",
                required = true)
    @Size(min = 2, max = 50)
    private String name;

    @ApiModelProperty(notes = "${error.userSignUpRequest.lastName.size}",
            required = true)
    @NotBlank
    @Size(min= 2, max = 50)
    private String lastName;

    /**
     * Api information for the fields of the api model
     */
    @ApiModelProperty(notes = "${error.userSignUpRequest.birthDate.past}",
            required = true)
    @NotBlank
    private String birthDate;
}

