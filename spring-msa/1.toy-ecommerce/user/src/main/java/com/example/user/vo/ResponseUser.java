package com.example.user.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseUser {
    private String email;
    private String name;
    private String userId;
}
