package com.example.userservice.controller;

import com.example.userservice.dto.UserCreationRequestDto;
import com.example.userservice.dto.UserCreationResponseDto;
import com.example.userservice.dto.UserSearchResponseDto;
import com.example.userservice.service.UserService;
import com.example.userservice.vo.Greeting;
import com.example.userservice.vo.UserCreationRequest;
import com.example.userservice.vo.UserCreationResponse;
import com.example.userservice.vo.UserSearchResponse;
import jakarta.validation.Valid;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<UserCreationResponse> createUser(@Valid @RequestBody UserCreationRequest userCreationRequest) {
        UserCreationResponseDto userCreationResponseDto = userService.createUser(UserCreationRequestDto.from(userCreationRequest));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userCreationResponseDto.toUserCreationResponse());
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserSearchResponse>> getUsers() {
        return ResponseEntity.ok(
                this.userService.getAllUsers().stream().map(UserSearchResponseDto::toUserSearchResponse).toList()
        );
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserSearchResponse> getUser(@PathVariable String userId) {
        return ResponseEntity.ok(
                this.userService.getUserByUserId(userId).toUserSearchResponse()
        );
    }
}
