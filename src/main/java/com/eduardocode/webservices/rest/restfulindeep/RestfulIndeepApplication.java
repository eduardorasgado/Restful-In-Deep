package com.eduardocode.webservices.rest.restfulindeep;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import javax.annotation.PostConstruct;
import java.util.TimeZone;

/**
 * <h1>RestfulIndeepApplication</h1>
 * Initial class and entrypoint to the company's web service application
 *
 * @author Eduardo Rasgado Ruiz
 */
@SpringBootApplication
public class RestfulIndeepApplication {

    @PostConstruct
    void init() {
        // Central daylight mexico city
        TimeZone.setDefault(TimeZone.getTimeZone("CDT"));
    }

    public static void main(String[] args) {
        SpringApplication.run(RestfulIndeepApplication.class, args);
    }
}
