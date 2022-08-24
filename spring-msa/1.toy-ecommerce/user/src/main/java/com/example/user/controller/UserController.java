package com.example.user.controller;

import com.example.user.vo.Greeting;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class UserController {

//    private final Environment env;
//
//    public UserController(Environment env) {
//        this.env = env;
//    }

    private final Greeting greeting;

    public UserController(Greeting greeting) {
        this.greeting = greeting;
    }

    @GetMapping("/hearth_check")
    public String status() {
        return "It's Working in User Service";
    }

    @GetMapping("/welcome")
    public String welcome() {
        return greeting.getMessage();
    }
}
