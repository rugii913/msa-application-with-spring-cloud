package com.example.userservice.service;

import com.example.userservice.dto.UserCreationRequestDto;
import com.example.userservice.dto.UserCreationResponseDto;
import com.example.userservice.dto.UserSearchResponseDto;
import com.example.userservice.jpa.UserEntity;
import com.example.userservice.jpa.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserCreationResponseDto createUser(UserCreationRequestDto userCreationRequestDto) {
        UserEntity userEntity = userRepository.save(
                new UserEntity(
                        userCreationRequestDto.email(),
                        userCreationRequestDto.name(),
                        passwordEncoder.encode(userCreationRequestDto.password())
                )
        );
        return UserCreationResponseDto.from(userEntity);
    }

    public UserSearchResponseDto getUserByUserId(String userId) {
        UserEntity user = userRepository.findByUserId(userId);
        if (user == null) throw new RuntimeException("User not found");

        return UserSearchResponseDto.of(user, Collections.emptyList());
    }

    public List<UserSearchResponseDto> getAllUsers() {
        Iterable<UserEntity> users = userRepository.findAll();

        return StreamSupport.stream(users.spliterator(), false)
                .map(user -> UserSearchResponseDto.of(user, Collections.emptyList())).toList();
    }
}
