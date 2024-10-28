package com.example.userservice.jpa;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor(force = true)
@Entity
@Table(name = "service_user")
public class UserEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, length = 50, unique = true)
    public final String email;

    @Column(nullable = false, length = 50)
    public final String name;

    @Column(nullable = false)
    public final String encryptedPassword;

    @Column(nullable = false, unique = true)
    public final String userId;

    @Column(nullable = false)
    public final LocalDateTime createdAt;

    public UserEntity(String email, String name, String encryptedPassword) {
        this.email = email;
        this.name = name;
        this.encryptedPassword = encryptedPassword;

        this.userId = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
    }
}
