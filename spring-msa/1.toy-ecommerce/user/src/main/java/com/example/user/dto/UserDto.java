package com.example.user.dto;

import com.example.user.repository.UserEntity;
import com.example.user.vo.ResponseOrder;
import com.example.user.vo.ResponseUser;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class UserDto {
    private String email;
    private String name;
    private String pwd;
    private String userId;
    private Date createdAt;

    private String encryptedPwd;

    private List<ResponseOrder> orders = new ArrayList<>();

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
