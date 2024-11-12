package com.example.userservice.security;

import com.example.userservice.dto.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.List;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtConfig.JwtConfigProperties jwtConfigProperties;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager, JwtConfig.JwtConfigProperties jwtConfigProperties) {
        super(authenticationManager);
        this.jwtConfigProperties = jwtConfigProperties;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginRequest loginRequest = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password(), List.of());

            return getAuthenticationManager().authenticate(authenticationToken);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        /*
         * super.successfulAuthentication(request, response, chain, authResult);
         * cf. super의 기본 로직인 AbstractAuthenticationProcessingFilter의 successfulAuthentication()은 어떻게 동작하는지 살펴보기
         * */
        String userId = ((CustomUser) authResult.getPrincipal()).getUserId();

        // cf. (별도 진행) java.util.Date는 사용하지 않고자 했으나, expiration 자체에서 Date type을 사용하므로 어쩔 수 없음, 대신 Date를 직접 다루지 않고, Instant에서 Date로 변환하도록 함
        // Instant now = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toInstant();
        Instant now = Instant.now();

        byte[] secretKeyBytes = Base64.getEncoder().encode(jwtConfigProperties.secretKeyString().getBytes());
        // cf. 가장 간단하게 SecretKey type 객체를 얻는 방법 - TODO 자세하게 보려면 Keys.hmacShaKeyFor() 메서드 로직을 확인할 것
        SecretKey secretKey = Keys.hmacShaKeyFor(secretKeyBytes);

        String accessToken = Jwts.builder()
                .subject(userId)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(jwtConfigProperties.expirationDuration())))
                .signWith(secretKey)
                .compact();

        response.addHeader("Access-Token", accessToken);
        response.addHeader("User-Id", userId);
    }
}
