package com.eduardocode.webservices.rest.restfulindeep.payload;

import com.eduardocode.webservices.rest.restfulindeep.model.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

/**
 * Class to be able to map user data from signup request in user resource
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSignUpRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String lastName;
    @NotBlank
    private String birthDate;
}

