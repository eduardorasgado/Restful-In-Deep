package com.eduardocode.webservices.rest.restfulindeep.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <h1>CustomizedResponseEntityExceptionHandler</h1>
 * Class to represent a global exception handler to be applied to all controllers in application
 * It uses {@link ResponseGeneralException} as virtual object with error data to be returned to client
 * It is a rest controller and it is applied to every resource available in application
 *
 * @author Eduardo Rasgado Ruiz
 * @see ResponseGeneralException
 */
@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Method to handle all the exceptions can occur in resources
     * @param ex
     * @param request
     * @return
     * @throws Exception
     */
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllException(Exception ex, WebRequest request)
            throws Exception {
        //
        ResponseGeneralException rge = new ResponseGeneralException(new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(rge, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Method to send a 404 not found status and {@link ResponseGeneralException} with not found
     * information directly as server response
     * @param ex
     * @param request
     * @return
     * @throws Exception
     */
    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(
            Exception ex, WebRequest request) throws Exception {
        ResponseGeneralException rge = new ResponseGeneralException(new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(rge, HttpStatus.NOT_FOUND);
    }

    /**
     * Method to send error details to client when a post does not exists in db
     * @param ex
     * @param request
     * @return
     * @throws Exception
     */
    @ExceptionHandler(PostNotFoundException.class)
    public final ResponseEntity<Object> handlePostNotFoundException(
            Exception ex, WebRequest request
    ) throws Exception {
        ResponseGeneralException  rge = new ResponseGeneralException(
                new Date(),
                ex.getMessage(),
                request.getDescription(false)
                );
        return new ResponseEntity<>(rge, HttpStatus.NOT_FOUND);
    }

    /**
     * Method that overrides a validation error in resources controllers, and send error details
     * in json to client with a bad request error.
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        List<ObjectError> errors = ex.getBindingResult().getAllErrors();
        List<String> messages = new ArrayList<>();
        for(ObjectError e : errors) {
            messages.add(e.getDefaultMessage());
        }

        ResponseGeneralException rge = new ResponseGeneralException(
                new Date(),
                "Data validation failure",
                messages.toString()
        );
        return new ResponseEntity<>(rge, HttpStatus.BAD_REQUEST);
    }
}
