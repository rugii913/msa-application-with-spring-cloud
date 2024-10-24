// sub project 공통 설정을 위한 root project 설정
subprojects {
    tasks.withType<Jar> {
        destinationDirectory.set(file("$rootDir/build/libs"))
    }
}
