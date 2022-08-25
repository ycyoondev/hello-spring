package com.example.user.controller;

import com.example.user.service.UserService;
import com.example.user.vo.Greeting;
import com.example.user.vo.RequestUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class UserController {

    private final UserService userService;
    private final Greeting greeting;

    @GetMapping("/hearth_check")
    public String status() {
        return "It's Working in User Service";
    }

    @GetMapping("/welcome")
    public String welcome() {
        return greeting.getMessage();
    }

    @PostMapping("/users")
    public String createUser(@RequestBody RequestUser user) {
        userService.createUser(user.toUserDto());
        return "Create user";
    }
}
