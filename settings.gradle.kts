// root project의 name 지정
rootProject.name = "msa-application-with-spring-cloud"

// root project가 포함할 sub project의 경로들 - sub project의 이름은 기본적으로는 path와 같게 지정됨
include(
    "apigateway-service",
    "discovery-service",
    "first-service",
    "second-service",
    "user-service",
    "catalog-service",
    "order-service",
    "config-service"
)
