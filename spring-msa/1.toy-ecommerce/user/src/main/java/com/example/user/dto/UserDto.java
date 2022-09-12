package com.example.user.dto;

import com.example.user.repository.UserEntity;
import com.example.user.vo.ResponseUser;
import lombok.Data;

import java.util.Date;

@Data
public class UserDto {
    private String email;
    private String name;
    private String pwd;
    private String userId;
    private Date createdAt;

    private String encryptedPwd;

    public UserDto(String email, String name, String pwd) {
        this.email = email;
        this.name = name;
        this.pwd = pwd;
    }

    public UserEntity toEntity() {
        return new UserEntity(email, userId, name, encryptedPwd);
    }

    public ResponseUser toResponseUser() {
        return new ResponseUser(email, name, userId);
    }
}
