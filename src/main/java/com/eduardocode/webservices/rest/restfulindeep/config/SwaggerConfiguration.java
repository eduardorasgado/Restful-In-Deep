package com.eduardocode.webservices.rest.restfulindeep.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * <h1>SwaggerConfiguration</h1>
 * This class represents all the swagger documentation configuration and beans.
 *
 * @author Eduardo Rasgado Ruiz
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    public static final Contact DEFAULT_CONTACT = new Contact(
            "Aaron Landeros", "", "aaron.landeros@gmamil.com");

    private static final ApiInfo CUSTOM_API_INFO = new ApiInfo(
            "My Company Documentation",
            "This api has been developed by a awesome company",
            "1.0",
            "urn:tos",
                         DEFAULT_CONTACT,
            "MIT",
            "",
            new ArrayList<VendorExtension>());

    private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES =
            new HashSet<String>(
                    Arrays.asList(
                            "application/json",
                            "application/xml"
                    )
            );

    /**
     * This method configures the default options for documentation
     * @return swagger docket
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                //custom api doc information
                .apiInfo(CUSTOM_API_INFO)

                // telling user in api doc client that api supports xml and json
                .produces(DEFAULT_PRODUCES_AND_CONSUMES)
                .consumes(DEFAULT_PRODUCES_AND_CONSUMES);
    }
}
