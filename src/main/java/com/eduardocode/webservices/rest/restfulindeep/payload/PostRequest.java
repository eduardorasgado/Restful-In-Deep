package com.eduardocode.webservices.rest.restfulindeep.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

/**
 * <h1>PostRequest</h1>
 * Class to be able to map a request body in case user wants to create a new post
 *
 * @author Eduardo Rasgado Ruiz
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {
    @NotBlank(message = "El titulo no puede ser vacío")
    @Size(min = 10, message = "El titulo debe contener al menos 10 caracteres")
    @Size(max = 40, message = "El titulo no puede tener mas de 40 caracteres")
    private String title;
    @NotBlank(message = "El contenido no puede estar vacio")
    @Size(min = 20, message = "El contenido debe ser al menos de 20 caracteres")
    @Size(max = 500, message = "El contenido no puede tener mas de 500 caracteres")
    private String content;
    private Set<String> tags;
}
