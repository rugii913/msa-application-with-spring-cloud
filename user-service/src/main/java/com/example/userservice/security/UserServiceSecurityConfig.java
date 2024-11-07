package com.example.userservice.security;

import com.example.userservice.service.AuthenticationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

// @EnableWebSecurity → 불필요
@Configuration
public class UserServiceSecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            AuthenticationService authenticationService,
            BCryptPasswordEncoder passwordEncoder
    ) throws Exception {
        /*
        * cf. http.getSharedObject(AuthenticationManagerBuilder.class)로 객체를 얻어 오지 않고,
        * - 메서드의 파라미터로 주입받으면 오류 발생
        * - Error creating bean with name 'securityFilterChain' defined in class path resource ... DaoAuthenticationConfigurer@7c97f39b to already built object ...
        * */
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(authenticationService).passwordEncoder(passwordEncoder);
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        /*
        * cf. OncePerRequestFilter의 subtype인 AuthenticationFilter와는 달리
        * - CustomAuthenticationFilter의 supertype인 UsernamePasswordAuthenticationFilter는 AbstractAuthenticationProcessingFilter의 subtype이고
        * - 따라서 CustomAuthenticationFilter는 AuthenticationFilter의 subtype은 아님
        * */
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManager);

        return http
                //.csrf(AbstractHttpConfigurer::disable) // csrfConfigurer -> csrfConfigurer.disable()를 넘길 경우, IDE에서 메서드 참조로 변경할 것을 추천함
                .csrf(csrfConfigurer -> csrfConfigurer
                        // csrfConfigurer.disable() 하지 않는 경우 GET 메서드 외 모든 메서드에 대해 CSRF를 신경써줘야 함 → 그렇지 않으면 403 Forbidden 응답
                        .ignoringRequestMatchers(
                                new AntPathRequestMatcher("/users/**"),
                                new AntPathRequestMatcher("/h2-console/**"),
                                new AntPathRequestMatcher("/login") // cf. POST이므로 추가하지 않으면 403 Forbidden만을 받게 됨
                        )
                )
                .authorizeHttpRequests(registry -> registry
                        .requestMatchers(
                                new AntPathRequestMatcher("/users", "POST"),
                                new AntPathRequestMatcher("/h2-console/**"),
                                new AntPathRequestMatcher("/health-check"),
                                new AntPathRequestMatcher("/welcome"),
                                // cf. "/error"를 추가하지 않는 경우, error 호출에 대한 권한이 없으므로, 원래 던져진 에러가 404든 500이든 상관 없이, 403 Forbidden만을 응답받게 됨
                                new AntPathRequestMatcher("/error")
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .authenticationManager(authenticationManager) // cf. 이 부분이 빠지면 Spring context 구성 시 오류(This object has already been built.)
                .addFilter(customAuthenticationFilter)
                .headers(headersConfigurer -> headersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)) // HeadersConfigurer.FrameOptionsConfig::disable
                .build();
    }
}
