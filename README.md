# MSA, Spring Cloud 예제
- 인프런 "Spring Cloud로 개발하는 마이크로서비스 애플리케이션(MSA)" 강의를 따라가는 예제
- 강의 자료
  - [GitHub 예시 파일](https://github.com/joneconsulting/toy-msa)
    - springboot3.2 브랜치 참고
  - https://github.com/joneconsulting/msa_with_spring_cloud → pdf 디렉토리에 pdf 자료
    - 강의 자료가 별도로 존재하므로 README.md에는 키워드만 메모하고자 함

## section 1. Microservice와 Spring Cloud의 소개

### 소프트웨어 아키텍처
- 간략한 IT 시스템 역사
  - (1960s ~ 1980s) 메인프레임, 하드웨어 중심 → 키워드: fragile, cowboys
  - (1990s ~ 2000s) 변화에도 어느 정도 성능 유지 → 키워드: robust, distributed
  - (2010s ~) flow of value의 지속적 개선 → 키워드: resilient/anti-fragile, cloud native
- antifragile의 네 가지 특징: auto scaling, microservices, chaos engineering, continuous deployments

### Cloud Native Architecture
- 기존 로컬 또는 사내 운영 환경의 시스템을 클라우드로 전환하기 위해 어떤 아키텍처를 가져야 하는가?
- cloud native architecture의 특징: 확장 가능한 아키텍처, 탄력적 아키텍처, 장애 격리(fault isolation)

### Cloud Native Application
- cloud native application: cloud native architecture에 의해 설계되고 구현되는 애플리케이션
- cloud native application의 구현 형태
  - Microservices
  - CI/CD: 지속적인 통합, 배포
  - DevOps: 개발 조직과 운영 조직의 통합(고객 및 서비스 관점 고려)
  - Containers: container 가상화

### 12 Factors
- 12 factors: cloud native application을 구축할 때 고려할 12가지 요소
  - Heroku 개발자들이 제시
  - (참고) https://12factor.net
- 12가지 요소(가이드)
  - (Codebase) 각 microservice에 대한 단일 코드 베이스, 버전 제어 목적, 코드의 통일적 관리
  - (Dependencies) 각 microservice는 전체 시스템에 영향을 주지 않으면서 수정 가능
  - (Config) 코드 외부의 구성 관리 도구를 통해 microservice에 필요한 작업을 제어
  - (Backing services) 분리된 보조 서비스 등을 이용하여 기능 추가 지원 가능
  - (Build, release, run) 빌드, 릴리즈, 실행 환경을 분리
  - (Processes) 각 microservice는 다른 microservice와 분리되어 독립적으로 실행, 공유 필요한 자원은 캐시 및 데이터 저장소와 같은 형태를 이용
  - (Port binding) 각 microservice는 자체 port에서 interface를 노출하여 다른 microservice와 격리
  - (Concurrency) 많은 수의 동일한 프로세스를 복사하는 방식으로 확장하여 부하 분산하기 위해 동시성 필요
  - (Disposability) 서비스 인스턴스를 종료, 삭제할 수 있어야 함
  - (Dev/prod parity) 개발, 운영 단계를 분리하여 서비스가 종속적이지 않도록 함
  - (Logs) 로그 작업은 애플리케이션 로직과 분리하여 이벤트 기반으로 동작
  - (Admin processes) 모든 microservice를 관리하기 위한 도구 필요
- 최근에 추가된 3가지 요소(Pivotal에서 제시)
  - (API first) 모든 microservice는 API 형태로 서비스 제공
  - (Telemetry) 모든 지표는 수치화, 시각화되어 관리할 수 있어야 함
  - (Authentication and authorization) microservice로 분리되어 운영되어도 API 사용 시 인증 및 인가 필요

### Monolithic vs. Microservice
- monolithic vs. front & back vs. microservice
  - monolithic → 하나의 애플리케이션 형태로 패키징, 모든 서비스의 내용들이 의존적
  - front & back → 서버와 클라이언트 애플리케이션 분리해서 개발
  - microservice → 각각의 서비스가 의미 있는 경계로 독립적, 영향 최소화
- microservice의 정의
  - Sam Newman → small autonomous services that work together
  - James Lewis and Martin Fowler → different programming languages, different data storage 가능

### Microservice Architecture란?
- microservice의 특징 정리
  - challenges
  - small well chosen deployable units
  - bounded context
  - RESTful
  - configuration management
  - cloud enabled
  - dynamic scale up and scale down
  - CI/CD
  - visibility
- 모든 것을 microservice로 개발해야하는가? (x) → microservice 도입 전 고려 사항
  - multiple rate of change
  - independent life cycles - 서비스 경계 관련
  - independent scalability
  - isolated failure
  - simplify interactions with external dependencies - 종속성은 낮게, 응집력은 높게
  - polyglot technology

### SOA vs. MSA
- SOA와 MSA의 차이
  - 서비스 공유에 대한 지향점
  - 기술 방식
- RESTful web service
  - Leonard Richardson의 성숙도 모델
    - (level 0) the swamp of POX
    - (level 1) resources
    - (level 2) HTTP verbs
    - (level 3) hypermedia controls
  - RESTful API 설계 시 고려 사항
    - consumer first
    - make best use of HTTP
    - HTTP의 request methods 적극 활용
    - 적절한 response status 사용
    - no secure info in URI
    - use plurals
    - use nouns for resources
    - for exceptions, define a consistent approach - 일관적인 endpoint로 진입점 단일화, API gateway

### Microservice Architecture Structures
- Gartner 조사 microservice architecture 컴포넌트 구성 예시
- service mash: MSA를 적용한 시스템의 내부 통신, 복잡한 내부 네트워크를 제어하는 추상적인 계층
- MSA 개발을 위한 기반 기술 참고
  - CNCF(Cloud Native Computing Foundation)에서 제시하는 [Cloud Native Interactive Landscape](https://landscape.cncf.io)

### Spring Cloud란?
- [Spring Cloud 공식 문서](https://spring.io/projects/spring-cloud)
  - Spring Cloud 사용 시 Spring Boot 필수, Spring Cloud의 각 하위 프로젝트와 호환되는 Spring Boot 버전 확인 필요
- 각 하위 프로젝트의 역할
  - centralized configuration management → Spring Cloud Config Server
  - location transparency → Naming Server(Eureka)
  - load distribution(load balancing) → Ribbon(Client Side), Spring Cloud Gateway
  - easier REST client → FeignClient
  - visibility and monitoring → Zipkin Distributed Tracing, Netflix API gateway
  - fault tolerance → Hystrix

## section 2. Service Discovery

### Spring Cloud Netflix Eureka 소개
- service discovery: 서비스를 찾을 수 있는 전화번호부로 비유, 서비스를 등록하고 등록된 검색
  - client는 load balancer 혹은 API gateway에 요청 정보를 전달하고 이들은 service discovery를 통해 적절한 서비스를 찾아낸 후 호출
  - Eureka 자체가 웹 서비스 성격으로 동작
- 사용 시 설정 설명
  - main()이 있는 클래스에 붙은 annotation @EnableEurekaServer 확인
  - application.yml에서 port, name, register-with-eureka, fetch-registry 설정

### Eureka Service Discovery - 프로젝트 생성 + 기본 설정
- 종속성으로 Eureka Server 추가 후 프로젝트 생성
- main()이 있는 클래스에 @EnableEurekaServer 붙임
- application.yml에서 port, name, register-with-eureka, fetch-registry 설정
- 설정된 포트에 브라우저로 요청하여 애플리케이션 동작 확인

### User Service - 프로젝트 생성 + discovery client 기본 설정, 동작 확인
- Eureka server의 client가 될 microservice 중 하나가 됨
- 종속성으로 다음 추가 후 프로젝트 생성
  - Eureka Discovery Client, Lombok, Spring Boot DevTools, Spring Web
- main()이 있는 클래스에 @EnableDiscoveryClient 붙임
  - @EnableEurekaClient를 붙여도 동작(@EnableEurekaClient가 @EnableDiscoveryClient의 구현)
- application.yml 설정
  - port, name 설정
  - register-with-eureka, fetch-registry true 설정, service-url.defaultZone 설정
- Eureka 대시보드(127.0.0.1:8761) 접속하여 등록된 user-service 인스턴스 확인
  - cf. 등록된 인스턴스 application의 이름은 대소문자를 구분하지 않음, 대시보드에서는 USER-SERVICE처럼 모두 대문자로 표시됨

### User Service - 등록 → 여러 user-service 프로세스 실행하여 Eureka 동작 확인
- cf. 강의에서는 Maven을 사용하였고, Gradle을 사용했기에 명령어가 다름
- (방법 1) IDE에서 여러 user-service 프로세스 실행하기
  - IntelliJ의 구성 편집(Edit Configurations) → 실행/디버그 구성(Run/Debug Configurations) 창 설정에서 작업
  - 기존 구성 복사 후 이름 중복되지 않도록 변경
  - 포트 중복되지 않도록 두번째 프로세스를 실행할 때 VM options(Java system properties가 됨)에 다음을 작성
    - `-Dserver.port=9002`
  - Eureka 대시보드(127.0.0.1:8761) 접속하여 등록된 user-service 인스턴스 수 확인
- (방법 2) Gradle task를 이용하여 실행
  - `./gradlew bootRun --args="--server.port=9003"`
  - `./gradlew bootRun --args="--server.port=9004"`
  - cf. [Spring 공식 문서 - Passing Arguments to Your Application](https://docs.spring.io/spring-boot/gradle-plugin/running.html#running-your-application.passing-system-properties)
  - cf. 강의에서는 다음을 사용
    - `mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Dserver.port=9003"`
    - Maven과 Gradle은 argument를 넘기는 방법도 다름
- (방법 3) 빌드 후 jar 파일 command line으로 실행
  - Gradle(혹은 Maven)을 사용하여 빌드
  - user-service의 settings.gradle.kts가 있는 경로에서 ./gradlew bootJar 명령어를 실행하여 빌드
    - cf. Maven인 경우 ./mvn compile package 등을 사용하여 빌드
  - user-service의 build/libs/user-service-xxx.jar 파일을 실행
    - `java -jar -Dserver.port=9003 ./build/libs/user-service-0.0.1-SNAPSHOT.jar`
    - `java -jar -Dserver.port=9004 ./build/libs/user-service-0.0.1-SNAPSHOT.jar`
      - cf. Maven인 경우 `java -jar -Dserver.port=9004 ./target/user-service-0.0.1-SNAPSHOT.jar`

### User Service - random port 인스턴스 시작 → Spring Boot의 random port 활용하여 여러 인스턴스 시작하기
- Spring Boot의 random port 지원
  - 문제 인식: 인스턴스를 시작할 때마다 포트 번호를 일일히 입력하는 것은 귀찮은 작업
  - 사용 방법: application.yml의 server.port를 0으로 기입
    - 알아서 충돌하지 않는 port로 시작
    - 서버 기동 시 -Dserver.port 등 포트를 지정하는 옵션을 모두 제거해줘야 함
    - 실행/디버그 구성(Run/Debug Configurations)에서도 포트를 명시한 구성은 제거
  - 포트 정보 확인 방법
    - 서버 기동 시 로그 확인
    - Eureka 대시보드의 Instances currently registered with Eureka에 있는
      - Status 부분 링크처럼 클릭할 수 있는 요소에 mouse over하면 브라우저 왼쪽 하단에 포트 번호 표시
  - 인스턴스 id 부여 방법 → Eureka 대시보드에서 인스턴스 정상적으로 표시하기
    - 문제 인식: random port를 활용한 인스턴스를 여러 개 띄우고 Eureka에서 확인해봐도
      - 192.168.0.1:uesr-service:0처럼 인스턴스는 하나만 표시될 것
      - application.yml에 입력된 포트 번호를 가져오기 때문
    - 인스턴스 id 부여하기
      - application.yml에 다음 입력
      - eureka.instance.instance-id: ${spring.cloud.client.hostname}:${spring.application.instance_id:${random.value}}

## section 3. API Gateway Service
- 사용자 혹은 외부 시스템으로부터 받은 요청을 단일화하여 처리하기

### API Gateway란?
- 라우팅 설정에 따라 endpoint로 client를 대신하여 요청하고, 응답을 받으면 client에 다시 전달하는 proxy 역할
  - 시스템 내부 구조는 숨기고, 외부 요청에 대해 적절한 형태로 가공하여 응답 가능
- MSA를 사용하면서 단일 진입점을 가진 형태가 필요
  - 각 microservice로의 모든 요청에 대한 진입 지점
  - client에서 각 microservice를 직접 호출하지 않게 함
    - client에서는 API gateway만 상대함
- API gateway에서 할 수 있는 기능
  - 인증 및 권한 부여
  - microservice 검색 통합
  - 응답 캐싱
  - 일괄적인 정책 유지, 회로 차단, QoS 다시 시도
  - 속도 제한
  - 부하 분산(load balancing)
  - 일괄적인 로깅, 추적, 상관 관계 등 기록
  - 헤더, 쿼리 문자열 등 변환
  - IP 허용 목록에 기반한 처리
- Spring Cloud에서 MSA 간 통신
  - 구현하기 위한 도구 → RestTemplate(IP, port 명시), FeignClient(인터페이스 사용, 서비스 이름으로 호출), ... 등
  - 그러면 통신 시 load balancer를 어디에 둘 것인가?
    - Ribbon → client side, 서비스 이름으로 호출 / 비동기 처리가 어려운 단점 / Spring Boot 2.4부터 maintenance
    - Zuul → server side, 라우팅, API gateway 역할 / Spring Boot 2.4부터 maintenance
      - [Ribbon, Zuul의 maintanence 상태 관련 참고](https://spring.io/blog/2018/12/12/spring-cloud-greenwich-rc1-available-now#spring-cloud-netflix-projects-entering-maintenance-mode)
    - 위 둘 대신 Spring Cloud Gateway를 사용할 것을 권장 - 비동기 처리 가능

### Netflix Zuul - 프로젝트 생성, Netflix Zuul - Filter 적용
- 이미 deprecated 표시된 지 오래되었고, 최신 Spring Boot에서는 사용할 수 없으므로 생략

### Spring Cloud Gateway란?
- API gateway 역할, routing 기능

### Spring Cloud Gateway - 프로젝트 생성 + routes 등록
- Eureka client로 등록할 first-service, second-service 프로젝트 생성(강의에서는 Netflix Zuul - 프로젝트 생성 부분에 포함된 내용)
  - 종속성: DeveloperTools의 Lombok, Web의 Spirng Web, Spring Cloud Discovery의 Eureka Discovery Client
  - 기능은 간단한 문자열 반환
- Spring Cloud Gateway 프로젝트 시작 시 종속성
  - DeveloperTools의 Lombok
  - Spring Cloud Discovery의 Eureka Discovery Client
  - Spring Cloud Routing의 Gateway
- apigateway-service
  - apigateway-service라는 artifact를 가진 Spring Cloud Gateway 생성
  - route 객체를 등록하도록 적절하게 application.yml 작성
    - cf. 단순히 predicates의 Path만 적을 경우 조건 만족 여부만 판단
      - 요청 url에서 중복되는 prefix 같은 부분을 자동으로 제거해주지 않음
- cf. 강의에서는 기본적으로 Tomcat이 아닌 Netty로 기동된다고 했지만 그 동안 변경사항이 있는 것으로 보임

### Spring Cloud Gateway - filter로서의 동작
- 참고 문서
  - Spring Cloud Gateway는 어떻게 동작하는가?
    - [Spring Cloud Gateway Reactive Server - How It Works](https://docs.spring.io/spring-cloud-gateway/reference/spring-cloud-gateway/how-it-works.html)
    - cf. [Spring Cloud Gateway Server MVC - How It Works](https://docs.spring.io/spring-cloud-gateway/reference/spring-cloud-gateway-server-mvc/how-it-works.html)
  - reactive gateway 구글링
    - [\[Spring\] spring cloud reactive gateway 사용시 주의할 점](https://bbidag.tistory.com/30)
    - [기타 블로그 - \[SCG\] Spring Cloud Gateway는 왜 Netty 기반으로 만들어졌을까? 그리고 WebFlux란?](https://mingyum119.tistory.com/252)
    - [기타 블로그 - Spring Cloud Gateway가 netty 기반 reactive web application으로 구동되는 이유](https://ykh6242.tistory.com/entry/Spring-Cloud-Gateway%EA%B0%80-netty-%EA%B8%B0%EB%B0%98-reactive-web-application%EC%9C%BC%EB%A1%9C-%EA%B5%AC%EB%8F%99%EB%90%98%EB%8A%94-%EC%9D%B4%EC%9C%A0)
- Spring Cloud Gateway 및 그 filter의 동작 방식
  - Spring Cloud Gateway는 client의 요청에 따라 서비스를 분기
  - 자세하게 보면
    - Gateway Handler Mapping: client로부터 어떤 요청이 들어왔는지, 요청 정보를 받음
    - Predicate: 사전에 설정한 조건, 이 조건에 따라 요청을 분기
    - Pre Filter, Post Filter: 요청, 응답 정보를 재구성
      - Post Filter 처리 이후 다시 Gateway Handler Mapping을 거쳐 응답이 client로 나감
  - Spring Cloud Gateway 설정 방식
    - Java 코드 방식
    - property 방식 → application.yml 파일 등에 설정 정보를 정의

### (별도 진행) Spring Cloud Gateway MVC 종속성을 선택했을 때 Java 코드를 활용한 gateway filter동작
- RouteLocator는 spring-cloud-starter-gateway-mvc에는 존재하지 않음
- 참고 문서
  - [How to Include Spring Cloud Gateway Server MVC](https://docs.spring.io/spring-cloud-gateway/reference/spring-cloud-gateway-server-mvc/starter.html)
  - [How It Works](https://docs.spring.io/spring-cloud-gateway/reference/spring-cloud-gateway-server-mvc/how-it-works.html)
  - [Java Routes API](https://docs.spring.io/spring-cloud-gateway/reference/spring-cloud-gateway-server-mvc/java-routes-api.html)
  - [Gateway Request Predicates
](https://docs.spring.io/spring-cloud-gateway/reference/spring-cloud-gateway-server-mvc/gateway-request-predicates.html#path-request-predicate)
  - [AddRequestHeader Filter](https://docs.spring.io/spring-cloud-gateway/reference/spring-cloud-gateway-server-mvc/filters/addrequestheader.html)
- 사용 방법
  - spring-cloud-starter-gateway가 아닌 spring-cloud-starter-gateway-mvc를 사용할 경우
    - RouteLocator 대신 org.springframework.web.servlet.function.RouterFunction 사용
  - @Bean을 붙이는 메서드에서 따로 builder를 주입받지 않고 RouterFunctions.route() static 메서드를 이용하여 빌더 시작
  - application.yml 설정과 비교해보면 
    - routes의 uri 부분은 Handlerfunction이 되고
    - predicates 부분은 RequestPredicates가 된다고 보면 될 것
  - 빌드된 RouterFunction에 대해 filter() 메서드 체이닝을 사용하려면 다음처럼 사용
    - HandlerFilterFunction.ofRequestProcessor(BeforeFilterFunctions...)
    - HandlerFilterFunction.ofResponseProcessor(AfterFilterFunctions...)

### (별도 진행) Spring Cloud Gateway MVC 종속성을 선택했을 때 application.yml 설정을 활용한 gateway filter 동작
- 앞서 `### Spring Cloud Gateway - 프로젝트 생성 + routes 등록`에서 진행했던 부분에서
  - 각 route에 filters만 추가
  - ex. filters: - AddRequestHeader=first-request, first-request-header - AddResponseHeader=first-response, first-response-header

### Spring Cloud Gateway - Java 코드로 filter 구성하기
- router 및 filter를 구성하는 Java 코드 작성
  - org.springframework.cloud.gateway.route.RouteLocator, ....builder.RouteLocatorBuilder 사용
  - application.yml 설정의 각 property와 대응되는 메서드를 사용
    - predicates - Path에 해당하는 path()
    - filters에 해당하는 filters()
    - uri에 해당하는 uri()
  - lambda(functional interface의 구현)를 사용하여 router 및 filter 구성
    - `Function<PredicateSpec, Buildable<Route>>`, `Function<GatewayFilterSpec, UriSpec>`
  - filters() 구성 시 addRequestHeader(), addResponseHeader()로 요청, 응답 헤더 추가 동작 확인

### Spring Cloud Gateway - application.yml 파일로 filter 구성하기
- 앞서 `### (별도 진행) Spring Cloud Gateway MVC 종속성을 선택했을 때 application.yml 설정을 활용한 gateway filter 동작`에서 작성한 파일에서 mvc 계층만 삭제

### Spring Cloud Gateway - custom filter
- filter 역할을 하는 custom filter bean을 등록하는 방식
  - 앞서 Java 코드 혹은 application.yml 프로퍼티 설정으로 라우터에 필터를 추가한 것과 다른 방식
  - 로그, 인증 처리, locale 변경 등 작업을 할 수 있음
    - 예를 들어 pre filter에서 JWT의 유효성 검사 등
- org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory를 구현하는 클래스를 @Component로 등록
  - AbstractGatewayFilterFactory의 apply(Config config): GatewayFilter 메서드 하나를 구현
  - 구현한 apply()의 동작이 필터(GatewayFilter)의 구현이 됨
  - cf. AbstractGatewayFilterFactory가 필요로 하는 generic type은 AbstractConfigurable과 관련
    - 특별한 configuration 정보가 필요할 경우 작성
    - 내부 static class로 작성해도 상관 없음
    - 특별히 필요한 config 정보가 없으면 내용이 없는 class로 놔두어도 잘 동작
- application.yml 각 gateway route 중 filters 부분에 작성한 filter 클래스 이름을 명시
  - 해당 filter를 필요로 하는 모든 라우트에 명시해줘야 함
- cf. GatewayFilter
  - (parameter) exchange: ServerWebExchange, chain: GatewayFilterChain
    - 비동기 방식인 reactive gateway에서는 exchange로부터 ServerHttpRequest, ServerHttpRespnse 타입 객체를 얻어올 수 있음
    - 동기 방식 서버(ex. Tomcat)에서 사용하는 HttpServletRequest, HttpServletResponse와는 타입 자체가 다름
  - (return) Mono<Void>
    - post filter를 사용하지 않는다면, chain.filter(exchange)를 호출해서 얻는 Mono<Void> 객체를 반환
    - post filter를 사용한다면
      - chain.filter(exchange)를 호출해서 얻는 Mono 객체에 대해 .then() 메서드 체이닝을 사용
      - .then(Mono.fromRunnable(...)) 과 같은 형태로 응답 시 filter 로직을 구현
    - cf. Spring WebFlux에서 사용하는 Mono는 최대 하나의 요소를 생성하는 리액티브 스트림
      - [Project Reactor docs](https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Mono.html)
        - then() 등 메서드에 대한 도식까지 나와있음
      - [기타 블로그 - Reactor 의 탄생, 생성과 구독 \(역사, Publisher 구현체 Flux와 Mono, defer, Subscriber 직접구현\)](https://sjh836.tistory.com/185)
      - [기타 블로그 - 2. Reactor Core \(Mono, Flux, Subscribe\)](https://beer1.tistory.com/17)

### Spring Cloud Gateway - global filter
- custom filter와 유사한 방식으로 작성 → AbstractGatewayFilterFactory 구현
- global filter와 custom filter와의 차이점
  - custom filter는 application.yml에서 개별 라우트에 적용할 필터를 명시했었던 것과는 달리
  - 별도로 명시하지 않고도, 모든 라우트에서 공통적으로 적용되는 필터로 동작
- cf. filter의 설정 정보 작성하기
  - AbstractGatewayFilterFactory의 generic으로 지정하는 클래스가 설정 정보의 데이터 클래스가 됨
  - application.yml에 설정 정보들을 명시하여 컨텍스트 구성 시 주입하게 되고
  - apply() 구현 시 주입받은 설정 정보들을 사용하도록 유연하게 작성할 수 있음
    - apply()가 parameter로 받은 config 객체가 generic에 명시된 데이터 클래스 객체임
- application.yml에 global filter 설정 명시
  - spring.cloud.gateway.default-filters로 name, (filter 설정 정보를 주입하는 경우) args 등을 명시
  - **custom filter와는 이 부분에서 차이**가 남
  - 자세한 방법은 예제 파일 참고
- filter 간 기본적인 실행 순서
  - pre filter의 경우, global filter가 custom filter보다 먼저 실행
  - post filter의 경우, global filter가 custom filter보다 나중에 실행
  - OrderedGatewayFilter 등을 이용하여 order를 지정할 경우 실행 순서는 바뀔 수 있음

### Spring Cloud Gateway - logging filter(OrderedGatewayFilter 타입을 이용하여 필터 동작 순서 조정)
- GatewayFilter의 subtype인 OrderedGatewayFilter를 작성하는 방식으로 진행
  - OrderedGatewatFilter 생성자의 파라미터 중 하나만 GatewayFilter delegate을 구현
  - cf. GatewayFilter 타입의 filter() 메서드의 파라미터인 ServerWebExchange exchange, GatewayFilterChain chain에 대해서 더 살펴보면 좋을 것
    - ServerWebExchange는 WebFlux에서 사용하는 타입
  - order 값은 org.springframework.core.Ordered.HIGHEST_PRECEDENCE 인터페이스의 상수를 활용하여 작성해보기
(활용 가능)
- custom filter에 args를 넘겨야 할 때 application.yml 작성 방법
  - 필터 클래스 이름만 명시하는 게 아니라 name, args 등을 구분하여 명시해줘야 함
  - second-service 쪽에만 logging filter를 등록해본 뒤 동작을 확인해보기
- OrderedGatewayFilter의 order에 따른 동작 확인
  - order가 Ordered.HIGHEST_PRECEDENCE로 지정된 경우, global filter보다 먼저 실행
  - Ordered.LOWEST_PRECEDENCE로 지정된 경우, 가장 마지막으로 실행

### Spring Cloud Gateway - load balancer + Eureka server와의 연동
- 지금까지 작성한 각 서비스들의 역할
  - Eureka server: 8761번 포트
    - service registration과 discovery 담당
  - first-service, second-service: 각 서비스를 가정한 간단한 서비스
    - first-service는 8081번 포트, second-service는 8082번 포트 → 여러 인스턴스를 실행하는 상황을 가정하여 랜덤 포트 지정 방식으로 변경, server.port = 0으로 지정
  - API gateway: 8000번 포트
    - 클라이언트로부터의 요청을 앞에서 받고, Eureka를 통해 service를 discovery
    - 요청 API에 따라 Eureka에서 각 service를 발견해내고, 각 service로 포워딩
- Eureka Server를 사용할 때의 API gateway의 property 작성
  - Eureka server를 naming server로 활용
    - 앞서 작성했던 application.yml의 routes의 각 property와는 상당히 다름
    - predicates는 그대로 두되, uri 부분에는 ip 주소, port 정보가 들어가지 않고, lb://...과 같은 방식으로 작성
      - ... 부분에는 Eureka에 등록된 각 서비스의 이름을 작성
      - `lb://` 는 이름을 이용해 naming service 안에 포함된 인스턴스를 찾겠다는 기호로 보면 됨
        - 정확하게는 load balancer를 가리키는 프로토콜
- 서비스 실행 순서는 되도록 Eureka server 먼저 실행
- 같은 서비스를 여러 인스턴스로 띄우고, gateway 동작 및 Eureka의 discovery, load balancing 동작 확인
  - server.port = 0으로 작성하여 랜덤 포트를 사용하도록 한 후 동작 확인
  - 환경 변수 정보 객체인 Environment를 주입 받은 후 API 호출 시 로그로 찍도록 하여 어떤 인스턴스가 호출됐는지 눈으로 확인해보기
  - round robin 방식으로 간단하게 로드 밸런싱 하고 있음을 확인할 수 있음

### (개인적인 생각) Spring Cloud Gateway + Eureka를 사용하는 이유?
- API gateway 기능이든, 요청 응답에 대한 필터링 기능이든, 로드 밸런싱 기능이든 모두
  - Nginx와 같은 웹 서버 등 다른 손 쉽고 잘 알려진 도구를 이용해도 충분히 구현 가능
- 그렇다면 Spring Cloud Gateway의 장점은?
  - gateway와 service discovery 등이 다른 서비스들과 더 잘 상호작용하는 방식의 시스템을 구성할 수 있는 장점이 있지 않을까 생각
  - 예를 들어 예제에서 보았듯 같은 마이크로 서비스의 여러 인스턴스가 기동 중일 때 ip 주소, 포트를 명시할 필요 없이
    - 각 서비스가 시작할 때 Eureka에 등록하도록 하고, Spring Cloud Gateway는 Eureka로부터 ip 주소, 포트를 가져와 해당 서비스로 포워딩
  - 그 외에도 간편하게 통합을 도와주는 장점이 있지 않을까 추측 중

## section 4. 예제 애플리케이션 전체 구성 개요
- 서비스 로직보다는 Spring Cloud와 Spring Boot를 활용한 microservice들을 만드는 것에 집중
- 전체 구성
  - registry service: Eureka server, microservice 등록 및 검색
  - routing service: API gateway server, microservice 부하 분산 및 서비스 라우팅
  - configuration service: Config Server, 각 microservice가 가져야할 프로파일 정보, 설정 정보
  - queuing system: Kafka 활용, microservice 간 메시지 발생 및 구독으로 데이터 동기화
  - 도메인 관련 microservice 
    - catalog-service: 상품 조회
    - user-service: 사용자 조회, 인증, 주문 확인
      - 클라이언트에서 주문 확인 요청 시 user-service는 order-service에 주문 조회 요청 
    - order-service: 상품 주문
      - 클라이언트에서 상품 주문 요청 시 order-service는 catalog-service에 상품 수량 업데이트 요청, 이 때 message queuing system 사용

## section 5. Users Microservice ①
- 1에서는 회원 가입, 회원 정보 확인, 전체 사용자 목록 조회 기능 구현 (cf. 2에서 로그인 기능 구현)
  - 우선 5개 API 구현 → 사용자 정보 등록, 전체 사용자 조회, 사용자 정보 및 주문 내역 조회, 작동 상태 확인, 환영 메시지
- 프로젝트 생성
  - Spring Initialzr 종속성 Web, Eureka Discovery Client, H2, Lombok, (DevTools)
  - main() 있는 클래스에 @EnableDiscoveryClient 붙임
  - application.yml 설정 → port, eureka instanceId, eureka service url
    - 추후 환경 변수 설정 및 h2 DB 설정 추가 
  - Environment 혹은 @Value를 활용한 환경 변수 주입 받기 연습
- H2 embedded DB 사용(강의와 약간 다른 방식으로 진행)
  - runtimeOnly로 h2 종속성 추가 
  - 강의와 다르게 H2 1.4 이상 버전을 사용(2.2.224 버전)
    - 사용자 경로(~)에 비어있는 msa-example-usr-service.mv.db 파일을 직접 만들고
    - h2-console에서 jdbc:h2:~\msa-example-user-service로 접속
  - 이 경우 자동으로 database 파일이 생성되지 않기 때문에 단순 embedded가 아닌 in-memory DB를 사용하기 까다로워짐
    - H2를 실행하여 in-memory DB를 생성해주거나, JPA가 자동으로 DB를 생성해주도록 유도해줘야함
    - 아직 JPA 종속성을 추가하지 않은 상황이므로, 직접 database 파일을 생성해주고 in-memory가 아닌 단순 embedded를 사용함

### user-service 사용자 추가 로직 작성
- 강의 Users Microservice - 사용자 추가 ~ JPA ② 관련
- 강의와 다르게 진행한 부분
  - spring-boot-starter-validation 종속성 추가 - jakarta.validation-api 종속성만으로는 validation이 동작하지 않음
    - controller에 누락된 jakarta.validation.Valid 어노테이션 추가
  - application.yml에 jpa.hibernate.ddl-auto 설정 명시
  - UserService 따로 interface를 만들지 않음
  - 사용자 테이블 이름을 users가 아닌 service_user로 함
  - request, response VO 및 DTO 클래스 이름을 다르게 함
    - request용 DTO와 response용 DTO 분리
  - 비밀번호를 나타내는 field 이름을 pwd가 아니라 password로 함
  - UserEntity의 encryptedPassword를 unique key로 하지 않음
  - modelmapper 라이브러리 사용하지 않음
    - cf. modelmapper를 이용하여 객체 간 매핑하는 예시 코드
      ```text
      ... 기타 import 생략
      import org.modelmapper.ModelMapper;
      
      class ... {
        public UserDto createUser(UserDto userDto) {
          ... 생략
          ModelMapper mapper = new ModelMapper();
          mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
          UserEntity userEntity = mapper.map(userDto, UserEntity.class);
          userEntity.setEncryptedPassword("...");
          ... 생략
        }
      }
      ```
  - modelmapper 라이브러리를 사용하지 않는 대신 DTO 쪽에 변환 로직을 만들어둠
    - DTO, VO에 Lombok 어노테이션을 사용하지 않고 record class 활용
    - 그 외 modelmapper 라이브러리를 사용하지 않는 대신 작성한 로직들 있음

### Spring Security 연동
- Spring Security 6.x부터 본격적으로 많은 변화가 있어, 강의보다는 최신화된 강의 예제 파일을 참고
- 예제 파일과 다르게 진행한 부분
  - password encoder 빈을 위한 config 파일은 별도로 분리
  - FrameOptionsConfig.disable() 대신 sameOrigin() 사용
  - csrf.disable() 대신 ignoringRequestMatchers() 사용

## section 6. Catalogs and Orders Microservice

### Users Microservice와 Spring Cloud Gateway 연동 → 내용상 user-service 관련
- apigateway-service의 application.yml에 user-service 쪽으로의 route 추가
  - Spring Cloud gateway를 통한 user-service 호출이 가능한지 확인
- 강의와 다르게 작성한 것
  - 각 endpoint url에 직접 "user-service"를 붙이지 않고
    - user-service의 application.yml에 server.servlet.context-path로 prefix를 일괄적으로 부여
    - (참고) [기타 블로그 - \[Spring\] url prefix 설정](https://sirzzang.github.io/dev/Dev-spring-controller-prefix/)

### Users Microservice - 사용자 조회
- 전체 사용자 목록 가져오기, 개별 사용자 가져오기 API 개발
  - 세부적인 코드는 강의 코드와 다르게 작성
- cf. @JsonInclude(JsonInclude.Include.NON_NULL)
  - null인 값인 경우 해당 field를 아예 넣지 않은 JSON을 만들어서 내보냄
- cf. Spring Security 관련하여 강의와 별도로 확인한 것들
  - csrfConfigurer.disable() 하지 않는 경우 GET 메서드 외 모든 메서드에 대해 CSRF를 신경써줘야 함 → 그렇지 않으면 403 Forbidden 응답
  - "/error"를 추가하지 않는 경우, error 호출에 대한 권한이 없으므로, 원래 던져진 에러가 404든 500이든 상관 없이, 503 Forbidden만을 응답받게 됨
  - permitAll 관련 읽어보기
    - [기타 블로그 - \[Spring Security\] - SecurityConfig 클래스의 permitAll\(\) 이 적용되지 않았던 이유](https://velog.io/@choidongkuen/Spring-Security-SecurityConfig-클래스의-permitAll-이-적용되지-않았던-이유)
    - [기타 블로그 - \[SpringBoot\] Spring Security Config에서 permitAll\(\)에 대한 진실과 오해](https://suhyeon-developer.tistory.com/42)

### \(별도 진행\) user-service 리팩토링
- 강의에서 작성한 클래스 코드에서 납득되지 않는 부분이 있기에 리팩토링 진행
- XxxRequest, XxxResponse는 VO 성격이 아니라고 판단하여 dto 패키지로 변경
- UserRepository가 CrudRepository가 아닌 ListCrudRepository를 상속하도록 변경
- presentation layer의 DTO와 business layer의 DTO 클래스가 분리되도록 하였으나,
  - 크게 실익이 없는 경우에는 별도로 나누지 않고 presentation layer의 DTO를 그대로 사용
  - ex. 요청 시의 DTO는 의존성이 다른 부분이 있다고 판단하여 분리된 채로 두고, 응답 시의 DTO는 의존성이 거의 다르지 않을 거라 판단하여 분리되지 않게 함
- 비밀번호 encode 작업을 entity 생성자 로직에서 진행 → 이 부분은 더 고민이 필요
- DTO 분리 관련 더 생각해볼 것
  - [인프런 강의 질문 - controller, service용 dto를 분리시키는 것에 대한 질문](https://www.inflearn.com/community/questions/944019) 
  - [기타 블로그 - Presentation - Business DTO를 분리시켜라](https://m-falcon.tistory.com/711)
    - 각 layer에서의 DTO 분리가 오버 엔지니어링이 되지 않을지 유의

### Catalogs Microservice - 개요 및 기능 구현
- 전체 카탈로그(상품 목록) 조회 API 개발
- 종속성 관련 user-service와 거의 유사하게 추가
  - spring-cloud-starter-netflix-eureka-client
  - spring-boot-starter-web
  - spring-boot-starter-validation → 추가하긴 했으나 현재 사용하지 않음
  - spring-boot-starter-data-jpa
  - h2 → 강의와 달리 1.4.x 이상 버전 사용함
  - devtools → 종속성으로 추가는 해두었으나 spring.devtools.livereload.enabled=false로 해둠
  - lombok
  - cf. Spring Security는 종속성으로 추가하지 않았음
- data.sql 및 application.yml의 프로퍼티 사용하여 애플리케이션 시작 시 데이터를 추가하게 하였음
  - 테스트를 용이하게 하기 위함
  - data.sql에 DML 작성
  - application.yml 설정
    - spring.jpa.defer-datasource-initialization=true → 강의 영상에 명시되어 있지 않으나 Hibernate 초기화된 이후에 SQL 스크립트가 실행되도록 하려면 필요
      - cf. Catalogs Microservice - 기능 구현 ① 강의 자료에 관련 내용 적혀 있음
      - 참고 자료
        - [기타 블로그 - data.sql이 동작하지 않을 때, 의심해봐야 할 것](https://devvkkid.tistory.com/262)
        - [기타 블로그 - Spring Boot/어플리케이션 실행할때 JPA 스키마 생성 및 ddl, dml sql 실행](https://dchkang83.tistory.com/43)
    - spring.jpa.generate-ddl은 강의에서 명시했으나 불필요
      - 참고 자료
        - [기타 블로그 - \[Spring Data JPA\] jpa.generate-ddl과 jpa.hibernate.ddl-auto 프로퍼티](https://sechoi.tistory.com/28)
        - [기타 블로그 - JPA 애플리케이션 데이터베이스 초기화](https://yeoon.tistory.com/132)
        - [기타 블로그 - Spring Boot 초기 데이터 설정 방법 정리\(data.sql, schema.sql\)](https://wildeveloperetrain.tistory.com/228)
        - [기타 블로그 - Spring에서 JPA / Hibernate 초기화 전략](https://pravusid.kr/java/2018/10/10/spring-database-initialization.html)
    - spring.sql.init.mode=always → 강의에 명시되어 있지 않으나 data.sql DML을 실행하기 위해 필요한 설정
- 강의와 다르게 작성한 것
  - 엔티티의 접근 제어자 및 생성자
  - DTO 클래스명
  - 강의에서 작성한 CatalogDto 클래스는 현재 사용하지 않으므로 추가하지 않았음
    - 필요 시 Catalogs Microservice - 기능 구현 ① 강의 14:10 부분 참고하여 작성
  - DB 초기화를 위한 DDL, DML 관련 application.yml 설정
  - DTO, Entity 클래스 작성 시 Serializable 구현 생략
    - 현재 당장은 Serializable를 구현하게 할 필요는 없다고 판단함
  
### Orders Microservice - 개요 및 기능 구현
- 주문 생성, 주문 조회, 특정 회원 전체 주문 조회 API 개발
- 종속성 및 설정
  - 종속성 관련 catalog-service 똑같이 추가
  - 초기 데이터는 넣지 않는 것으로 하여 application.yml에서 DDL 설정은 제외함
- 코드는 user-service, catalog-service와 유사하게 작성 
- 강의와 다르게 작성한 것
  - catalog-service와 유사, 납득이 안 되는 부분은 강의와 다르게 작성함
- cf. order는 H2 DBMS에서 예약어이므로 테이블 이름을 product_order로 정함 
- cf. 데이터 무결성 관련
  - entity 간의 연관 관계가 없으므로, 통상적으로 JPA를 사용하는 방식으로는 데이터의 무결성이 지켜지지 않음
  - 예를 들어 Product entity의 product_id가 CATALOG-001 ~ 003까지 있을 때
  - product_id가 CATALOG-009인 OrderEntity를 생성해도 오류가 발생하지 않음
  - 하지만 MSA에서 데이터 무결성은 JPA의 연관 관계를 맺는 방식으로 지키는 것이 아니라고 함
- (별도 진행) cf. order entity를 save()한 뒤 return 객체의 createdAt 필드가 null인 문제 상황
  - Hibernate의 @ColumnDefault(value = "CURRENT_TIMESTAMP")를 사용할 경우, return 객체의 createdAt을 DB의 값대로 반영하려면
    - entityManager.refresh(savedEntity)와 비슷한 방식으로 해당 entity에 대해 refresh가 필요함
  - 불가피한 경우가 아니라면 Hibernate의 @CreationTimestamp를 이용하여 생성 시의 시간을 기록할 수 있음
    - default가 있는 DB column의 created_at 값을 반영하는 대신 @CreationTimestamp을 사용하여 서버에서 시간을 기록하게 하는 것

## section 7. Users Microservice ②
- JWT, API gateway의 AuthorizationHeaderFilter를 이용한 로그인 기능

### Users Microservice - AuthenticationFilter 추가, loadUserByUsername() 구현
- Spring Security에 많은 변화가 있었기 때문에 강의 영상보다는 새로운 예제 코드 및 기타 문서들을 참고하여 작성

#### CustomAuthenticationFilter 및 AuthenticationService 추가 등
- CustomAuthenticationFilter 작성
  - 관련하여 로그인 시 사용자 인증 정보를 담을 LoginRequest 작성
    - HttpServletRequest 객체에서 getInputStream()으로 읽어낸 body를 jackson ObjectMapper로 매핑
  - UsernamePasswordAuthenticationFilter의 두 메서드 구현
    - attemptAuthentication()
      - UsernamePasswordAuthenticationFilter의 필드인 AuthenticationManager 객체의 authenticate()를 호출하여 인증을 위임
      - AuthenticationManager 객체는 SecurityFilterChain 객체를 반환하는 security 설정 메서드에서 지정해둠
    - successfulAuthentication()
      - 로그인 성공 시의 로직이 들어갈 부분 - 아직 로직을 상세히 작성하지는 않음
- AuthenticationService 작성
  - cf. 강의에서는 UserService에 바로 작성했으나, 나는 별도의 클래스로 분리함
  - AuthenticationService에서 이용하기 위해 UserRepository에 findByEmail() 추가
  - AuthenticationService는 UserDetailsService를 구현하도록 하여 loadUserByUsername() 메서드를 구현
    - loadUserByUsername()은 UserDetails 객체를 반환 
    - UserDetailsService는 단순한 인터페이스로 loadUserByUsername() 외 다른 멤버가 없음, 당연히 default 메서드도 없음
  - AuthenticationService의 역할은 username(혹은 email과 같이 사용자를 식별할 수 있는 것)을 이용해
    - UserEntity처럼 사용자 정보가 담긴 객체를 가져와서
    - Spring Security에서 인증에 사용할 수 있는 사용자 정보인 UserDetails 객체로 바꿔주는 역할

#### Spring Security config 설정 - UserServiceSecurityConfig 수정
- path 관련 설정
  - csrf 무시하도록 일단 설정해둠 - "/login" path에 대해 
  - authorization 설정
    - "/users" path로 오는 POST에 대해서는 permitAll()
    - 다른 path에 대해서는 authenticated()로 인증이 필요하도록 설정
- authentication manager 관련 작업
  - http.getSharedObject(AuthenticationManagerBuilder.class)로 AuthenticationManagerBuilder 객체를 받아옴
    - cf. 단순하게 빈으로 등록된 authenticationManagerBuilder를 받아오려고 하면 Spring context 구성 시 실패함(DaoAuthenticationConfigurer가 이미 빌드된 객체라고 함)
  - 이후 이 authenticationManagerBuilder에 userDetailsService와 passwordEncoder를 세팅해줌
    - userDetailsService로는 앞서 작성한 AuthenticationService 사용
    - passwordEncoder로는 미리 작성해둔 BCryptPasswordEncoder 객체 사용
  - 그 후 authenticationManagerBuilder.build()로 AuthenticationManager 객체를 얻음
  - 이 AuthenticationManager 객체를 SecurityFilterChain에 authenticationManager로도 등록해둠
    - ex. http. ... .authenticationManager(authenticationManager)
    - cf. 이 부분이 빠지면 Spring context 구성 시 오류(This object has already been built.)
      - 이미 build()가 호출되어 구성된 AuthenticationManager가 있는데,
      - SecurityFilterChain 구성 시 명시해주지 않으면, 또 다른 AuthenticationManager 기본 객체를 구성하려고 시도해서 발생하는 오류라고 추정함
      - 자세한 원인은 알아볼 필요가 있을 듯함  
- 인증 처리를 위한 filter 추가 작업
  - 위에서 만들어둔 AuthenticationManager 객체를 인자로 넘겨 CustomAuthenticationFilter 객체를 생성한 후
  - SecurityFilterChain 구성 시 addFilter()로 추가
  - cf. 강의에서는 AuthenticationFilter라는 이름의 클래스를 만들고, 계속 해당 이름을 언급했지만 약간 오해의 소지가 있다고 생각함
    - Spring Security에 이미 AuthenticationFilter라는 클래스가 있고
    - 이 AuthenticationFilter는 OncePerRequestFilter의 subtype임
    - 그런데 CustomAuthenticationFilter의 supertype인 UsernamePasswordAuthenticationFilter는 AbstractAuthenticationProcessingFilter의 subtype이고
    - 따라서 새로 작성한 CustomAuthenticationFilter는 AuthenticationFilter의 subtype은 아님
  - 이렇게 추가한 filter가 의존하는 AuthenticationManager를 이용해서 인증 작업을 진행하는데
    - 위에서 설정해두었듯 AuthenticationManager는 UserDetailsService와 PasswordEncoder에 의존하고 있고
    - UserDetailsService로 username 등이 일치하는 사용자의 정보를 바탕으로 UserDetails 객체를 구성함
    - 이렇게 구성된 UserDetails 객체와 LoginRequest로부터 구성된 UsernamePasswordAuthenticationToken의 정보를
      - PasswordEncoder 등을 활용하여 비교하는 과정이 있다고 생각하면 됨

#### Spring Security 기본 제공 로그인 사용
- 프로젝트(user-service)에 명시적으로 "/login" path 및 이에 매핑되는 메서드를 추가하지 않았으나
  - Spring Security를 구성해둔 것만으로 기본적으로 "/login" 및 이에 매핑되는 로그인 메서드를 이용할 수 있음
- 이 자동 구성된 로그인 메서드에서 다음을 이용해 인증을 처리
  - 위에서 작성해둔 CustomAuthenticationFilter(UsernamePasswordAuthentication의 subtype)
  - AuthenticationManagerBuilder를 이용해 구성한 뒤 security filter chain에 등록해둔 AuthenticationManager
  - 그 밖의 CustomAuthenticationFilter에서 이용하도록 작성해둔 코드들

#### (별도 확인) Spring Security를 사용하는 서버 API 호출 시 응답 상태 및 메시지 등 관련하여 알아둘 점
- 강의 진행과 별도로 API 호출 시도 중 Spring Security를 사용할 때의 기본적인 서버 응답 때문에 혼란을 겪은 부분이 있었음
- (1) path "/error"로 연결할 수 없는 경우(ex. "/error"가 permitAll()이 아님)
  - 오류가 없는 요청에서는 문제 없이 동작하나
  - 오류 발생 시 403 Forbidden status를 갖고 있으나, body가 비어있는 응답을 받음 
- (2) GET이 아닌 메서드에 대해 적절히 CSRF 보호 처리가 되지 않은 경우
  - status: 403(Forbidden)
  - error: Forbidden
  - message: Forbidden
- 이하 (3) ~ (7) 예시는 "/error" 연결 및 CSRF 보호 처리에 대해서는 문제 없음을 가정 
- (3) authentication이 필요한 API를 authentication 없이 호출한 경우
  - status: 403(Forbidden)
  - error: Forbidden
  - message: Access Denied
- (4) anyRequest().authenticated()일 때 서버에 없는 path로 요청을 한 경우 - 위 (1)과 같은 응답(강의에서 다소 잘못 설명)
  - status: 403(Forbidden)
  - error: Forbidden
  - message: Access Denied
  - 왜 이런 응답을 보내는가?
    - servlet에서 path에 매핑되는 적절한 메서드로 연결하기 전에 Security filter chain에서 인증, 인가를 확인
    - 특정 path를 제외한 모든 path에 대해 authenticated()일 것을 요구했으나
    - (그 path가 사용자에 의해 정의됐는지 여부와 상관 없이) authentication 정보가 없기 때문에 AccessDenied 메시지를 보내는 것
- (4-1) cf. anyRequest().authenticated()일 때 서버에 path는 있으나 잘못된 method로 요청한 경우(ex. POST /login이 아닌 GET /login 요청)
  - 위 (4)와 같음
- (5) cf. anyRequest().permitAll() 일 때 서버에 없는 path로 요청을 한 경우
  - status: 404(Not Found)
  - error: Not Found
  - message: No static resource xxx...
  - - cf. security filter chain은 문제 없이 통과했으나 서버 내부에서 던진 오류
- (6) 올바르지 않은 형식(ex. Content-Type을 JSON으로 지정했으나 JSON에 맞지 않는 형식으로 보낸 경우)
  - status: 500(Internal Server Error)
  - error: Internal Server Error
  - message: 서버에서 던진 에러 메시지
  - cf. "서버에서 던진 에러 메시지"라고 표현했듯, security filter chain에서 던진 오류가 아니라 서버 내부에서 던진 오류
- (7) 인증 정보가 맞지 않는 경우(ex. "/login" 요청 시 일치하는 아이디 없음, 비밀번호 불일치)
  - status: 401(Unauthorized)
  - error: Unauthorized
  - message: Unauthorized

### Users Microservice - Routes 정보 변경, Routes 테스트
- API gateway routes 정보 변경
  - gateway에 들어올 때는 어떤 microservice를 호출할지 판단하기 위해 "/user-service" prefix를 붙여서 받지만
  - microservice를 호출할 때는 "/user-service" prefix를 없애고 호출할 수 있도록
  - gateway route의 filter를 적절히 설정함
    - 정규 표현식 활용
    - filters의 RewritePath=... 활용
- user-service 쪽 application.yml에서 server.servlet.context-path로 설정해둔 url prefix 설정은 제거함

### Users Microservice - 로그인 처리 과정, 로그인 성공 처리, JWT 생성
- 사용자의 email과 password 입력값을 이용한 인증 성공 후 JWT를 생성하는 작업

#### (별도 확인) Spring Security 관련 디버깅 시 중단점을 설정할 곳들
- (1) 직접 작성하여 bean으로 등록한 클래스 혹은 SecurityFilterChain을 반환하는 메서드 내에서 구성했으나 직접 객체를 생성한 경우
  - 직접 작성한 코드 내에 중단점 설정 
- (2) SecurityFilterChain을 반환하는 메서드 내에서 구성했으며 직접 객체를 생성하지 않고 HttpSecurity 객체의 메서드 체이닝을 사용하여 구성된 security 로직
  - FilterChainProxy의 내부 클래스인 VirtualFilterChain의 doFilter(ServletRequest request, ServletResponse response)의 nextFilter.doFilter(request, response, this); 부분
  - 왜 FilterChainProxy의 doFilter(ServletRequest request, ServletResponse response, FilterChain chain)에 중단점을 걸지 않는가?
    - FilterChainProxy의 doFilter()는 Spring Security에서 가장 앞 쪽으로 드러난 진입점이 되는 filter로 이해하면 될 것
    - 실제로 SecurityFilterChain을 반환하는 메서드에서 등록된 filter들이 동작하는 곳이 VirtualFilterChain의 doFilter()
    - 두 doFilter()에 모두 중단점을 걸고 프레임을 확인해보면
      - FilterChainProxy의 doFilter()에서 FilterChainProxy 내부의 doFilterInternal()을 호출한 뒤
      - doFilterInternal()의 this.filterChainDecorator.decorate(reset, filters).doFilter(firewallRequest, firewallResponse) 코드 이후로
      - SecurityFilterChain을 반환하는 메서드에서 등록된 filter 혹은 Spring Security에서 기본적으로 등록시킨 filter들이
        - VirtualFilterChain의 doFilter()의 코드 nextFilter.doFilter(request, response, this)를 따라 차곡차곡 call stack을 쌓으면서 호출되는 것을 확인해볼 수 있음
  
#### 로그인 성공 시 토큰 발급 작업
- 인증 filter(UsernamePasswordAuthenticationFilter의 subtype)의 successfulAuthentication() 구현
  - application.yml에서 JWT의 만료 시간, 비밀 키 정보를 갖고 있도록 하고, 이를 successfulAuthentication()에서 JWT를 build하는 데에 사용
- 강의와 다르게 작성한 부분
  - 강의와 다르게 UserService(혹은 내 경우 AuthenticationService)에 getUserDetailsByEmail()을 작성하지 않음
    - 강의에서 작성한 메서드는 이름과도 다르게 UserDetails 객체를 return하지 않고, UserDto를 return했으며
      - 이 같은 방식이 아니더라도 충분히 구현 가능하다고 판단함
    - UserDetails의 subtype을 return하는 loadUserByUsername()(내가 수정한 코드에서는 아래에서 설명할 loadUserByEmail())에서
      - UserDetails의 getUsername()이 호출될 때 userId 값을 가져가도록 return new CustomUser(userEntity.userId, userEntity.encryptedPassword, ...)와 같이 구현
      - 위에서 CustomUser는 아래에서 설명할 새로 정의한 wrapper class
    - 덕분에 CustomAuthenticationFilter에서 UserService(혹은 내가 수정한 코드에서는 AuthenticationService)에 의존하지 않게 됨
      - 애초에 AuthenticationService의 loadUserByUsername()(내가 수정한 코드에서는 아래에서 설명할 loadUserByEmail())에서
      - 꼭 필요한 정보를 담아서 return 하도록 작업한 것
  - 강의와 다르게 UserDetailsService의 wrapper인 CustomUserDetailsService, User의 wrapper인 CustomUser 작성
    - AuthenticationService에서 사용하는 메서드의 이름이 loadUserByUsername()인 것이 부적절하다고 느낌
      - 이에 따라 UserDetailsService의 wrapper인 CustomUserDetailsService를 작성하여 loadUserByEmail()을 정의
      - CustomUserDetailsService는 UserDetailsService를 상속
        - loadUserByUsername()이 loadUserByEmail()을 호출하도록 오버라이딩
      - AuthenticationService는 CustomUserDetailsService를 구현하도록 하여 AuthenticationService에서는 loadUserByEmail()을 구현하게 함
    - authentication 과정에서 JWT payload의 subject로 사용할 것을 userId로 정했는데, Spring Security 제공 User의 subject는 username인 점이 부적절하다고 느낌
      - User의 wrapper인 CustomUser를 작성하여 생성 시의 parameter 이름이 username이 아닌 userId로 보이도록 하고,
        - 실제로는 super() 생성자 호출 시 username에 넣도록 함
      - CustomUser에 getUserId()를 정의하여 supertype인 User의 username 값을 가져오도록 구현
      - 이에 따라 Spring Security에서 구성된 Authentication type 객체의 principal을 CustomUser로 type casting하면
        - getUserId()로 (실제로는 username 필드에 저장된) userId를 얻어오도록 하고
        - 이를 이용해 JWT를 구성할 수 있도록 함
  - 강의와 다르게 JWT를 build하는 데에 필요한 정보를 application.yml에서 받아올 때
    - Environment 객체를 이용하지 않고
    - @EnableConfigurationProperties와 @ConfigurationProperties를 사용
      - 자세한 내용은 JwtConfig 클래스 소스 코드를 참고 

### Users Microservice - JWT 처리 과정, AuthorizationHeaderFilter 추가, 테스트
- (email과 password가 아닌) 로그인 성공 후 발급된 JWT를 활용, 이를 검증하여 요청을 호출한 클라이언트가 인증된 사용자임을 확인하는 작업
- session 방식이 아닌 JWT(bearer token) 방식 사용
  - bearer authentication
    - API를 호출할 때 access token을 API 서버에 제출하여 인증 처리
    - OAuth를 위해 고안된 방법(RFC 6750)

#### apigateway-service에서 JWT를 검증하는 필터 추가 및 적용
- apigateway-service에 JWT를 검증하는 필터를 작성(AuthorizationHeaderFilter)
  - 로그인 성공 후 발급된 JWT를 검증하여 인증된 사용자임을 확인하는 것
  - 작성된 필터를 user-service에서 GET 메서드인 API들에 적용함 → application.yml의 routes 설정
- 강의와 다르게 작성한 부분
  - jjwt 버전 관련 - 강의에서 apigateway-service의 AuthorizationHeaderFilter에 사용한
    - setSigningKey()는 deprecated → verifyWith() 사용, verifyWith() 후 build()로 jwtParser를 가져와야 함
    - JwtParser의 parseClaimsJws() 역시 deprecated → parseSignedClaims() 사용
    - Jws\< io.jsonwebtoken.Claims \>(혹은 Jws)의 getBody() 역시 deprecated → getPayload() 사용
  - 강의에서는 apigateway-service에 jaxb-api를 추가했으나, 추가하지 않아도 동작함
    - 강의에서는 이를 추가하지 않으면 java.lang.NoClassDefFoundError: javax/xml/bind/DatatypeConverter 오류 발생
    - 아마도 내 코드에서는 runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.6") 종속성을 별도로 추가했기 때문이 아닌가 추측함
