plugins {
    id("org.springframework.boot") version "2.2.5.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

configurations {
    implementation {
        resolutionStrategy.failOnVersionConflict()
    }
}

dependencies {
    listOf(
            "spring-boot-starter-webflux",
            "spring-boot-starter-reactor-netty",
            "spring-boot-starter"
    ).forEach { name ->
        implementation("org.springframework.boot:${name}") {
            exclude(module = "spring-boot-starter-logging")
        }
    }

    implementation("org.springframework.boot:spring-boot-starter-log4j2")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(module = "junit")
        exclude(module = "mockito-core")
    }

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.1")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.6.1")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.6.1")
    testImplementation("org.junit.vintage:junit-vintage-engine:5.6.1")

    testImplementation("org.junit.platform:junit-platform-commons:1.6.1")
    testImplementation("org.junit.platform:junit-platform-runner:1.6.1")
    testImplementation("org.junit.platform:junit-platform-launcher:1.6.1")
    testImplementation("org.junit.platform:junit-platform-engine:1.6.1")

}

tasks.test {
    useJUnitPlatform()
}