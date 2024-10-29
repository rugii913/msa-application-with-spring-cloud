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
                .csrf(csrfConfigurer -> csrfConfigurer.ignoringRequestMatchers("/h2-console/**"))
                .authorizeHttpRequests(registry -> registry
                        .requestMatchers(
                                new AntPathRequestMatcher("/users/**"),
                                new AntPathRequestMatcher("/h2-console/**"),
                                new AntPathRequestMatcher("/health-check"),
                                new AntPathRequestMatcher("/welcome")
                        ).permitAll()
                )
                .headers(headersConfigurer -> headersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)) // HeadersConfigurer.FrameOptionsConfig::disable
                .build();
    }
}
