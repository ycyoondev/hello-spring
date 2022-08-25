package com.example.user.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 50, unique = true)
    private String email;
    private String userId;
    private String name;
    private String encryptedPwd;

    public UserEntity(String email, String userId, String name, String encryptedPwd) {
        this.email = email;
        this.userId = userId;
        this.name = name;
        this.encryptedPwd = encryptedPwd;
    }
}
