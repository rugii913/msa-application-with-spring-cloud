package com.example.userservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

// @EnableWebSecurity → 불필요
@Configuration
public class UserServiceSecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                //.csrf(AbstractHttpConfigurer::disable) // csrfConfigurer -> csrfConfigurer.disable()를 넘길 경우, IDE에서 메서드 참조로 변경할 것을 추천함
                .csrf(csrfConfigurer -> csrfConfigurer
                        // csrfConfigurer.disable() 하지 않는 경우 GET 메서드 외 모든 메서드에 대해 CSRF를 신경써줘야 함 → 그렇지 않으면 403 Forbidden 응답
                        .ignoringRequestMatchers(
                                new AntPathRequestMatcher("/users/**"),
                                new AntPathRequestMatcher("/h2-console/**")
                        )
                )
                .authorizeHttpRequests(registry -> registry
                        .requestMatchers(
                                new AntPathRequestMatcher("/users/**"),
                                new AntPathRequestMatcher("/h2-console/**"),
                                new AntPathRequestMatcher("/health-check"),
                                new AntPathRequestMatcher("/welcome"),
                                // cf. "/error"를 추가하지 않는 경우, error 호출에 대한 권한이 없으므로, 원래 던져진 에러가 404든 500이든 상관 없이, 503 Forbidden만을 응답받게 됨
                                new AntPathRequestMatcher("/error")
                        ).permitAll()
                )
                .headers(headersConfigurer -> headersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)) // HeadersConfigurer.FrameOptionsConfig::disable
                .build();
    }
}
