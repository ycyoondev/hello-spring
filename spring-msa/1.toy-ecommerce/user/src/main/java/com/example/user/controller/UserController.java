package com.example.user.controller;

import com.example.user.dto.UserDto;
import com.example.user.repository.UserEntity;
import com.example.user.service.UserService;
import com.example.user.vo.Greeting;
import com.example.user.vo.RequestUser;
import com.example.user.vo.ResponseUser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class UserController {

    private final UserService userService;
    private final Greeting greeting;
    private final Environment env;

    @GetMapping("/hearth_check")
    public String status() {
        return String.format("It's Working in User Service on Port %s",
                env.getProperty("local.server.port"));
    }

    @GetMapping("/welcome")
    public String welcome() {
        return greeting.getMessage();
    }

    @PostMapping("/users")
    public ResponseEntity createUser(@RequestBody RequestUser user) {
        UserDto userDto = userService.createUser(user.toUserDto());
        return ResponseEntity.status(HttpStatus.CREATED).body(userDto.toResponseUser());
    }

    @GetMapping("/users")
    public ResponseEntity<List<ResponseUser>> getUsers() {
        Iterable<UserEntity> userList = userService.getUserByAll();
        List<ResponseUser> result = new ArrayList<>();
        userList.forEach(v -> {
            result.add(new ResponseUser(v.getEmail(),
                    v.getName(), v.getUserId()));
        });

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<ResponseUser> getUsers(@PathVariable String userId) {
        UserDto userDto = userService.getUserByUserId(userId);
        ResponseUser responseUser = new ResponseUser(userDto.getEmail(), userDto.getName(), userDto.getUserId());
        return ResponseEntity.ok().body(responseUser);
    }
}
