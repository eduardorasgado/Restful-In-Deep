package com.eduardocode.webservices.rest.restfulindeep.payload;

import com.eduardocode.webservices.rest.restfulindeep.model.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Temporal;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * Class to be able to map user data from signup request in user resource
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSignUpRequest {
    @NotBlank(message = "El nombre no debe estar vacío")
    @Size(min = 2, message = "El nombre no puede ser menor a 2 caracteres")
    @Size(max = 50, message = "El nombre no puede ser mayor a 50 caracteres")
    private String name;
    @NotBlank(message = "El apellido no debe estar vacío")
    @Size(min = 2, message = "El apellido no puede ser menor a 2 caracteres")
    @Size(max = 50, message = "El apellido no puede ser mayor a 50 caracteres")
    private String lastName;
    @NotBlank(message = "La fecha de nacimiento no debe estar vacía")
    private String birthDate;
}

