package com.example.user.service;

import com.example.user.dto.UserDto;
import org.springframework.stereotype.Service;

public interface UserService {
    UserDto createUser(UserDto userDto);
}
