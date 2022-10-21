package com.example.user.service;

import com.example.user.dto.UserDto;
import com.example.user.repository.UserEntity;
import org.springframework.stereotype.Service;

public interface UserService {
    UserDto createUser(UserDto userDto);
    UserDto getUserByUserId(String userId);
    Iterable<UserEntity> getUserByAll();
}
