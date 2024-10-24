package com.example.secondservice;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/second-service")
public class SecondServiceController {

    Environment env;

    public SecondServiceController(Environment env) {
        this.env = env;
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to the Second Service!";
    }

    @GetMapping("/message")
    public String message(@RequestHeader("second-request") String header) {
        log.info(header);
        return "Hello World in Second Service!";
    }

    @GetMapping("/check")
    public String check(HttpServletRequest request) {
        log.info("Server port = {}", request.getServerPort()); // request에서 server port를 확인하는 방법

        // 추상화된 환경 변수 객체 Environment에서 server port를 확인하는 방법
        // cf. server.port는 0으로 명시했으므로 실제 할당된 정보를 확인하려면 local.server.port를 가져와야 함
        return String.format("Hi, there. This is a message from Second Service on PORT %s!", env.getProperty("local.server.port"));
    }
}
