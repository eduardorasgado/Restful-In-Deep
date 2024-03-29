package com.eduardocode.webservices.rest.restfulindeep.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * <h1>PostNotFoundException</h1>
 * Define an error that can be throw in post request from a certain existing user
 *
 * @author Eduardo Rasgado Ruiz
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class PostNotFoundException extends RuntimeException {
    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public PostNotFoundException(String message) {
        super(message);
    }
}
