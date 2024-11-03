package com.example.userservice.service;

import com.example.userservice.dto.UserCreationServiceRequestDto;
import com.example.userservice.dto.UserCreationServiceResponseDto;
import com.example.userservice.dto.UserSearchServiceResponseDto;
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

    public UserCreationServiceResponseDto createUser(UserCreationServiceRequestDto requestDto) {
        UserEntity userEntity = userRepository.save(
                new UserEntity(
                        requestDto.email(),
                        requestDto.name(),
                        requestDto.password(),
                        passwordEncoder
                )
        );
        return UserCreationServiceResponseDto.from(userEntity);
    }

    public UserSearchServiceResponseDto getUserByUserId(String userId) {
        UserEntity user = userRepository.findByUserId(userId);
        if (user == null) throw new RuntimeException("User not found");

        return UserSearchServiceResponseDto.of(user, Collections.emptyList());
    }

    public List<UserSearchServiceResponseDto> getAllUsers() {
        List<UserEntity> users = userRepository.findAll();

        return users.stream().map(user -> UserSearchServiceResponseDto.of(user, Collections.emptyList())).toList();
    }
}
