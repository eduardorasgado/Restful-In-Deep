package com.eduardocode.webservices.rest.restfulindeep.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * <h1>SwaggerConfiguration</h1>
 * This class represents all the swagger documentation configuration and beans.
 *
 * @author Eduardo Rasgado Ruiz
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    /**
     * This method configures the default options for documentation
     * @return swagger docket
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2);
    }
}
