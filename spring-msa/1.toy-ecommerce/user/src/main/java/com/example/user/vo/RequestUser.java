package com.example.user.vo;

import com.example.user.dto.UserDto;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RequestUser {
    @NotNull(message = "Email cannot be null")
    @Size(min = 2, message = "more 2 characters")
    private String email;

    @NotNull(message = "name cannot be null")
    @Size(min = 2, message = "more 2 characters")
    private String name;

    @NotNull(message = "pwd cannot be null")
    @Size(min = 2, message = "more 2 characters")
    private String pwd;


    public UserDto toUserDto() {
        return new UserDto(email, name, pwd);
    }
}
