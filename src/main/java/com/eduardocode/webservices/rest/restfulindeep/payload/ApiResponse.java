package com.eduardocode.webservices.rest.restfulindeep.payload;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class to Represent a server response to client to return the state and message of
 * some action accomplished in server.
 *
 * @author Eduardo Rasgado Ruiz
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(description = "All details about an api response, it can return error/success info")
public class ApiResponse {
    @ApiModelProperty(notes = "${apiResponse.model.status.notes}", required = true)
    private String status;
    @ApiModelProperty(notes = "${apiResponse.model.message.notes}")
    private String message;
}
