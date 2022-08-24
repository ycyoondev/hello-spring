package com.example.user.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RequestUser {
    @NotNull(message = "Email cannot be null")
    @Size(min = 2, message = "more 2 characters")
    private String email;

    @NotNull(message = "Email cannot be null")
    @Size(min = 2, message = "more 2 characters")
    private String name;

    @NotNull(message = "Email cannot be null")
    @Size(min = 2, message = "more 2 characters")
    private String pwd;
}
