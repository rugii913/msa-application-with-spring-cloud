package com.example.userservice.service;

import com.example.userservice.dto.UserCreationRequestDto;
import com.example.userservice.dto.UserCreationResponseDto;
import com.example.userservice.jpa.UserEntity;
import com.example.userservice.jpa.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserCreationResponseDto createUser(UserCreationRequestDto userCreationRequestDto) {
        UserEntity userEntity = userRepository.save(userCreationRequestDto.toEntity());
        return UserCreationResponseDto.from(userEntity);
    }
}
