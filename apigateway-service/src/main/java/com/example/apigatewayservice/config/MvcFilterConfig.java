package com.example.apigatewayservice.config;

import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.cloud.gateway.server.mvc.predicate.GatewayRequestPredicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.AfterFilterFunctions.addResponseHeader;
import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.addRequestHeader;

@Configuration
public class MvcFilterConfig {

    /*
    * Spring Cloud Gateway Server MVC에서 router 사용하기 → README.md의 "(별도 진행) Spring Cloud Gateway MVC 종속성을 선택했을 때 Java 코드를  활용한 gateway 동작" 부분 참고
    * */
    @Bean
    public RouterFunction<ServerResponse> gatewayRoutes() {
        // cf. RouterFunctions.route(RequestPredicate predicate, HandlerFunction<T> handlerFunction)는 바로 RouterFunction<T>를 return 함
        // - 빌더로 메서드 체인을 사용하고 싶다면 RouterFunctions.route()으로 Builder 객체를 가져온 뒤 Builder의 route()를 사용해야 함
        return RouterFunctions.route()
                .route(GatewayRequestPredicates.path("/first-service/**"), HandlerFunctions.http("http://localhost:8081"))
                .before(addRequestHeader("first-request", "first-request-header"))
                .after(addResponseHeader("first-response", "first-response-header"))
                .route(GatewayRequestPredicates.path("/second-service/**"), HandlerFunctions.http("http://localhost:8082"))
                .before(addRequestHeader("second-request", "second-request-header"))
                .after(addResponseHeader("second-response", "second-response-header"))
                .build();
        // cf. (잘못 동작하는 코드) 이렇게 작성할 경우 "/first-service/**", "/second-service/**"으로 요청을 보내는 경우 모두에 first... second... 헤더가 붙어 나감
    }
}
