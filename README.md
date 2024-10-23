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
  - 자세한 방법은 예제 파일 참고
- filter 간 기본적인 실행 순서
  - pre filter의 경우, global filter가 custom filter보다 먼저 실행
  - post filter의 경우, global filter가 custom filter보다 나중에 실행

