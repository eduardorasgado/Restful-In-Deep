package com.eduardocode.webservices.rest.restfulindeep.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h1>HelloWorldController</h1>
 * Rest controller example to test the api
 *
 * @author Eduardo Rasgado Ruiz
 */
@RestController
public class HelloWorldController {
    // GET
    // URI - /hellow-world
    // method - "Hello world"s
    @RequestMapping(method = RequestMethod.GET, path = "/hello-world")
    public String helloworld() {
        return "Hello world";
    }

    @GetMapping("/hello-world-2")
    public String helloworld2() {
        return "Again hello world";
    }
}
