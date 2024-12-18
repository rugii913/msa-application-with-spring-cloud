plugins {
    java
    id("org.springframework.boot") version "3.3.4"
    id("io.spring.dependency-management") version "1.1.6"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

extra["springCloudVersion"] = "2023.0.3"

dependencies {
    // implementation("org.springframework.cloud:spring-cloud-starter-gateway-mvc") // spring-cloud-starter-gateway(reactive gateway)를 사용하면서 주석 처리
    // implementation("org.springframework:spring-webmvc") // spring-cloud-starter-gateway(reactive gateway)를 사용하면서 주석 처리
    implementation("org.springframework.cloud:spring-cloud-starter-gateway")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")

    // caffeine cache
    implementation("com.github.ben-manes.caffeine:caffeine")

    // jjwt
    /*
    * cf. io.jsonwebtoken:jjwt:xxx는 legacy로 분류됨, 라이브러리 공식 문서에서 jjwt-api, jjwt-impl, jjwt-jackson을 별도로 가져오길 권장함
    * - (참고) https://github.com/jwtk/jjwt?tab=readme-ov-file#gradle, https://github.com/jwtk/jjwt?tab=readme-ov-file#understanding-jjwt-dependencies
    * */
    implementation("io.jsonwebtoken:jjwt-api:0.12.6")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.6")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.6")

    // spring-cloud-starter-config, spring-cloud-starter-bootstrap
    // cf. https://docs.spring.io/spring-cloud-config/reference/client.html#config-first-bootstrap
    implementation("org.springframework.cloud:spring-cloud-starter-config")
    implementation("org.springframework.cloud:spring-cloud-starter-bootstrap")

    // spring-boot-starter-actuator
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    // lombok
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    // test 관련
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
