package com.example.userservice.controller;

import com.example.userservice.vo.Greeting;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

// @RequestMapping("/") // @RequestMapping("/")은 아무것도 적지 않은 것과 같음
@RestController
public class UserController {

    private final Environment env;
    private final Greeting greeting;

    public UserController(Environment env, Greeting greeting) {
        this.env = env;
        this.greeting = greeting;
    }

    @GetMapping("/health-check")
    public ResponseEntity<String> status() {
        return ResponseEntity.ok("user-service works well");
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
}
