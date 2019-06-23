package com.eduardocode.webservices.rest.restfulindeep.controller;

import org.springframework.web.bind.annotation.*;

/**
 * <h1>HelloWorldController</h1>
 * Rest controller example to test the api
 *
 * @author Eduardo Rasgado Ruiz
 */
@RestController
@RequestMapping("/api")
public class HelloWorldController {
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
}
