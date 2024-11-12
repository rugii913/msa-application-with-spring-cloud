package com.example.userservice.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Configuration
@EnableConfigurationProperties(JwtConfig.JwtConfigProperties.class)
public class JwtConfig {

    private final JwtConfigProperties jwtConfigProperties;

    public JwtConfig(JwtConfigProperties jwtConfigProperties) {
        this.jwtConfigProperties = jwtConfigProperties;
    }

    @Bean
    JwtConfigProperties jwtConfigProperties() {
        return jwtConfigProperties;
    }

    @ConfigurationProperties("jwt-config")
    public record JwtConfigProperties(
            Duration expirationDuration,
            String secretKeyString
    ) {

        @ConstructorBinding // cf. 생성자가 여러 개인 경우 @ConstructorBinding을 명시해줘야 함
        /*
        * cf. application.yml의 값들은 기본적으로 모두 String 값이지만, Spring에서 binding 시 이 정도 변환은 해결해줌
        * - 반면 Environment에서 값을 직접 꺼내오는 방식을 택하면 String 값을 parsing 해주는 작업을 직접 진행해줘야 함
        * */
        public JwtConfigProperties(long expirationDurationInMinutes, String secretKeyString) {
            this(Duration.of(expirationDurationInMinutes, ChronoUnit.MINUTES), secretKeyString);
        }
    }
}
