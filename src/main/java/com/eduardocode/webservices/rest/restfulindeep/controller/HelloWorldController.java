package com.eduardocode.webservices.rest.restfulindeep.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

/**
 * <h1>HelloWorldController</h1>
 * Rest controller example to test the api
 *
 * @author Eduardo Rasgado Ruiz
 */
@RestController
@RequestMapping("/api")
public class HelloWorldController {

    /**
     * wiring internationalization bean
     */
    @Autowired
    private MessageSource messageSource;

    // GET
    // URI - /hellow-world
    // method - "Hello world"s
    @RequestMapping(method = RequestMethod.GET, path = "/hello-world")
    public String helloworld() {
        return "Hello world";
    }

    /**
     * Returning a bean which is automatically converted into a json
     * @return
     */
    @GetMapping("/hello-world-bean")
    public HelloWorldBean helloworldBean() {
        return new HelloWorldBean("Hello world bean");
    }

    @GetMapping("/hello-world-bean/{name}")
    public HelloWorldBean helloWorldBean(@PathVariable("name") String name,
                                         @RequestParam("apellidos") String apellidos){
        StringBuilder message = new StringBuilder();
        message.append("Hello ");
        message.append(name);
        message.append(" ");
        if(apellidos != null) {
            message.append(apellidos);
        }
        return new HelloWorldBean(message.toString());
    }

    /**
     * Method to test the internalization beans and resource bundle
     * @param locale
     * @return
     */
    @GetMapping("/hello-world-internationalized")
    public String helloWorldInternationalized(
            @RequestHeader(name = "Accept-Language", required=false) Locale locale) {
        return messageSource.getMessage("good.morning.message", null, locale);
    }
}
