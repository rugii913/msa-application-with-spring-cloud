package com.example.apigatewayservice.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class MvcFilterConfig {

    /*
    * Spring Cloud Gateway Server MVC에서 router 사용하기 → README.md의 "(별도 진행) Spring Cloud Gateway MVC 종속성을 선택했을 때 Java 코드를  활용한 gateway 동작" 부분 참고
    * */
    // application.yml을 이용한 설정 사용 및 spring-cloud-starter-gateway 종속성으로 라우팅 및 필터 기능을 사용하기 위해 주석 처리
    /*
    @Bean
    public RouterFunction<ServerResponse> gatewayRoutes() {
        // cf. RouterFunctions.route(RequestPredicate predicate, HandlerFunction<T> handlerFunction)는 바로 RouterFunction<T>를 return 함
        // - 빌더로 메서드 체인을 사용하고 싶다면 RouterFunctions.route()으로 Builder 객체를 가져온 뒤 Builder의 route()를 사용해야 함
        final var firstServiceRouterFunction = RouterFunctions
                .route(GatewayRequestPredicates.path("/first-service/**"), HandlerFunctions.http("http://localhost:8081"))
                .filter(HandlerFilterFunction.ofRequestProcessor(BeforeFilterFunctions.addRequestHeader("first-request", "first-request-header-filter")))
                .filter(HandlerFilterFunction.ofResponseProcessor(AfterFilterFunctions.addResponseHeader("first-response", "first-response-header-filter")));

        final var secondServiceRouterFunction = RouterFunctions
                .route(GatewayRequestPredicates.path("/second-service/**"), HandlerFunctions.http("http://localhost:8082"))
                .filter(HandlerFilterFunction.ofRequestProcessor(BeforeFilterFunctions.addRequestHeader("second-request", "second-request-header-filter")))
                .filter(HandlerFilterFunction.ofResponseProcessor(AfterFilterFunctions.addResponseHeader("second-response", "second-response-header-filter")));

        return firstServiceRouterFunction.and(secondServiceRouterFunction);
    }
     */
}
