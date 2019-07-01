package com.eduardocode.webservices.rest.restfulindeep.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * <h1>ResponseGeneralException</h1>
 * General exception response structure to send error information to cliente
 * Used as message container in {@link CustomizedResponseEntityExceptionHandler}
 *
 * @author Eduardo Rasgado Ruiz
 * @see CustomizedResponseEntityExceptionHandler
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseGeneralException {
    private Date timestamp;
    private String message;
    private String details;

}
