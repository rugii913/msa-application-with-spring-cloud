package com.example.apigatewayservice.filter;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.util.Base64;

@Slf4j
@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

    private final SecretKey secretKey;

    public AuthorizationHeaderFilter(
            @Value("${jwt-config.secret-key-string}") String secretKeyString
    ) {
        super(Config.class); // cf. 명시하지 않으면 Spring context 구성 과정에서 ClassCastException 발생
        byte[] secretKeyBytes = Base64.getEncoder().encode(secretKeyString.getBytes());
        this.secretKey = Keys.hmacShaKeyFor(secretKeyBytes);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            // Custom Pre Filter
            ServerHttpRequest request = exchange.getRequest();

            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, "No authorization header", HttpStatus.UNAUTHORIZED);
            }

            String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).getFirst();

            String jwt = authorizationHeader.replace("Bearer ", "");
            if (!isJwtValid(jwt)) {
                return onError(exchange, "JWT is not valid", HttpStatus.UNAUTHORIZED);
            }

            return chain.filter(exchange);
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, String error, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);

        log.info(error);
        return response.setComplete();
    }

    private boolean isJwtValid(String jwt) {
        // cf. 강의에서는 subject를 가져오는 부분을 try catch로 묶어줬으나, 그럴 필요성을 느끼지 못해 사용하지 않았음
        JwtParser jwtParser = Jwts.parser().verifyWith(secretKey).build();
        String subject = jwtParser.parseSignedClaims(jwt).getPayload().getSubject();

        if (subject == null || subject.isEmpty()) {
            return false;
        }

        return true;
    }


    public static class Config {}
}
