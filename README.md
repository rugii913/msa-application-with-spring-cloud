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

### Eureka Service Discovery - 프로젝트 생성 및 기본 설정
- 종속성으로 Eureka Server 추가 후 프로젝트 생성
- main()이 있는 클래스에 붙은 @EnableEurekaServer 확인
- application.yml에서 port, name, register-with-eureka, fetch-registry 설정
- 설정된 포트에 브라우저로 요청하여 애플리케이션 동작 확인
