plugins {
    java
    idea
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

repositories {
    mavenCentral()
}

dependencies {
    listOf(
            "spring-boot-starter-webflux",
            "spring-boot-starter-reactor-netty",
            "spring-boot-starter"
    ).forEach { name ->
        implementation("org.springframework.boot:${name}") {
            exclude(module="spring-boot-starter-logging")
        }
    }

    implementation("org.springframework.boot:spring-boot-starter-log4j2")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(module = "junit")
        exclude(module = "mockito-core")
    }
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")

}


