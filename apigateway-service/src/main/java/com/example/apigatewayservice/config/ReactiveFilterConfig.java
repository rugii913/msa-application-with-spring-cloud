package com.example.apigatewayservice.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReactiveFilterConfig {

    @Bean
    /*
    * cf. reactive에서도 RouterFunction을 그대로 사용할 수 있는 듯함
    * - 다만 RouteLocatorBuilder를 사용하면
    *   - 여러 route를 등록할 때 조금 더 매끄럽게 메서드 체이닝할 수 있고
    *   - application.yml의 설정과 이름이 더 잘 일치하는 느낌
    * */
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("first-service", predicateSpec -> predicateSpec
                        .path("/first-service/**")
                        .filters(gatewayFilterSpec -> gatewayFilterSpec
                                .addRequestHeader("first-request", "first-request-header")
                                .addResponseHeader("first-response", "first-response-header")
                        )
                        .uri("http://localhost:8081")
                )
                .route("second-service", predicateSpec -> predicateSpec
                        .path("/second-service/**")
                        .filters(gatewayFilterSpec -> gatewayFilterSpec
                                .addRequestHeader("second-request", "second-request-header")
                                .addResponseHeader("second-response", "second-response-header")
                        )
                        .uri("http://localhost:8082")
                )
                .build();
    }
}
