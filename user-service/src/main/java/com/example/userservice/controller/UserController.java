package com.example.userservice.controller;

import com.example.userservice.dto.*;
import com.example.userservice.service.UserService;
import com.example.userservice.vo.Greeting;
import jakarta.validation.Valid;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// @RequestMapping("/") // @RequestMapping("/")은 아무것도 적지 않은 것과 같음
@RestController
public class UserController {

    private final Environment env;
    private final Greeting greeting;
    private final UserService userService;

    public UserController(Environment env, Greeting greeting, UserService userService) {
        this.env = env;
        this.greeting = greeting;
        this.userService = userService;
    }

    @GetMapping("/health-check")
    public ResponseEntity<String> status() {
        return ResponseEntity.ok(
                String.format("user-service works well on port %s", env.getProperty("local.server.port"))
        );
    }

    @GetMapping("/welcome")
    public ResponseEntity<String> welcome() {
        String greetingMessage1 = env.getProperty("greeting.message");
        String greetingMessage2 = greeting.getMessage();

        String concatenatedGreetingMessage = String.format(
                "greeting message from Environment object: %s\ngreeting message from Greeting object(using @Value): %s",
                greetingMessage1,
                greetingMessage2
        );

        return ResponseEntity.ok(concatenatedGreetingMessage);
    }

    @PostMapping("/users")
    public ResponseEntity<UserCreationResponseDto> createUser(@Valid @RequestBody UserCreationRequestDto requestDto) {
        UserCreationResponseDto userCreationResponseDto = userService.createUser(UserCreationServiceRequestDto.from(requestDto));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userCreationResponseDto);
    }

    @GetMapping("/users")
    public ResponseEntity<UserListResponseDto> findAllUsers() {
        return ResponseEntity.ok(
                this.userService.findAllUsers()
        );
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserSearchResponseDto> findUser(@PathVariable String userId) {
        return ResponseEntity.ok(
                this.userService.findUser(userId).toUserSearchResponseDto()
        );
    }
}
