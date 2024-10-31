package com.example.userservice.vo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data // @Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode + 필요한 경우 constructor는 private으로 두고 static factory method 사용 가능
@Component
public class Greeting {

    @Value("${greeting.message}") // 환경 변수 greeting.message 주입
    private String message;
}
