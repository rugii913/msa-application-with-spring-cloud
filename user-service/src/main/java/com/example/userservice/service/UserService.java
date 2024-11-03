package com.example.userservice.service;

import com.example.userservice.dto.*;
import com.example.userservice.jpa.UserEntity;
import com.example.userservice.jpa.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserCreationResponseDto createUser(UserCreationServiceRequestDto requestDto) {
        UserEntity userEntity = userRepository.save(
                new UserEntity(
                        requestDto.email(),
                        requestDto.name(),
                        requestDto.password(),
                        passwordEncoder
                )
        );
        return UserCreationResponseDto.from(userEntity);
    }

    public UserSearchServiceResponseDto findUser(String userId) {
        UserEntity foundUser = userRepository.findByUserId(userId);
        if (foundUser == null) throw new RuntimeException("User not found");

        return UserSearchServiceResponseDto.of(foundUser, Collections.emptyList());
    }

    public UserListResponseDto findAllUsers() {
        List<UserEntity> users = userRepository.findAll();

        return new UserListResponseDto(users.stream().map(UserListItemDto::from).toList());
    }
}
