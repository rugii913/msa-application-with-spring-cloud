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
    // spring-cloud-starter-netflix-eureka-client
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")

    // spring-boot-starter-web
    implementation("org.springframework.boot:spring-boot-starter-web")

    // spring-boot-starter-validation
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // jakarta validation-api
    // jakarta.validation-api만으로는 validation이 동작하지 않고, hibernate-validator와 같은 구현체가 있어야 동작함에 유의
    // 참고 - https://velog.io/@appti/Validator-학습-로그
    // implementation("jakarta.validation:jakarta.validation-api")

    // spring-boot-starter-data-jpa
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // h2
    runtimeOnly("com.h2database:h2:2.2.224")

    // spring-boot-starter-security
    implementation("org.springframework.boot:spring-boot-starter-security")

    // jjwt
    /*
    * cf. io.jsonwebtoken:jjwt:xxx는 legacy로 분류됨, 라이브러리 공식 문서에서 jjwt-api, jjwt-impl, jjwt-jackson을 별도로 가져오길 권장함
    * - (참고) https://github.com/jwtk/jjwt?tab=readme-ov-file#gradle, https://github.com/jwtk/jjwt?tab=readme-ov-file#understanding-jjwt-dependencies
    * */
    implementation("io.jsonwebtoken:jjwt-api:0.12.6")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.6")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.6")

    // devtools
    developmentOnly("org.springframework.boot:spring-boot-devtools")

    // lombok
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    // modelmapper // 강의와 다르게 사용하지 않음 - 가장 최신 버전도 CVE vulnerability를 갖고 있기에 종속성에 추가하지 않았음
    // implementation("org.modelmapper:modelmapper:3.2.1")

    // test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
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
