package com.example.apigatewayservice.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class CustomFilter extends AbstractGatewayFilterFactory<CustomFilter.Config> {

    public CustomFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            // Custom Pre Filter
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            log.info("(Custom PRE filter) request id: {}", request.getId());

            // Custom Post Filter
            return chain
                    .filter(exchange)
                    .then(Mono.fromRunnable(
                            () -> log.info("(Custom POST filter) response status code: {}", response.getStatusCode()))
                    );
        };
    }

    public static class Config {
        // 특별한 configuration 정보가 필요할 경우 여기에 작성
        // 이 Config class를 다루는 로직은 AbstractConfigurable에 있음
    }
}
