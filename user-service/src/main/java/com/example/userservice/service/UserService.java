package com.example.userservice.service;

import com.example.userservice.dto.UserCreationServiceParameterDto;
import com.example.userservice.dto.UserCreationServiceReturnDto;
import com.example.userservice.dto.UserSearchServiceReturnDto;
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

    public UserCreationServiceReturnDto createUser(UserCreationServiceParameterDto userCreationRequestDto) {
        UserEntity userEntity = userRepository.save(
                new UserEntity(
                        userCreationRequestDto.email(),
                        userCreationRequestDto.name(),
                        passwordEncoder.encode(userCreationRequestDto.password())
                )
        );
        return UserCreationServiceReturnDto.from(userEntity);
    }

    public UserSearchServiceReturnDto getUserByUserId(String userId) {
        UserEntity user = userRepository.findByUserId(userId);
        if (user == null) throw new RuntimeException("User not found");

        return UserSearchServiceReturnDto.of(user, Collections.emptyList());
    }

    public List<UserSearchServiceReturnDto> getAllUsers() {
        Iterable<UserEntity> users = userRepository.findAll();

        return StreamSupport.stream(users.spliterator(), false)
                .map(user -> UserSearchServiceReturnDto.of(user, Collections.emptyList())).toList();
    }
}
