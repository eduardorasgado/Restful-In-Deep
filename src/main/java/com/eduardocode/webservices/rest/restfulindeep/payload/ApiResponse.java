package com.eduardocode.webservices.rest.restfulindeep.payload;

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
public class ApiResponse {
    private String status;
    private String message;
}
