import com.google.protobuf.gradle.*
import org.gradle.kotlin.dsl.provider.gradleKotlinDslOf

val grpcVersion = "1.31.0"
val jupiterVersion = "5.6.3"
val junitPlatformVersion = "1.6.3"

plugins {
    id("org.springframework.boot") version "2.3.8.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    id("org.sonarqube") version "2.8"
    id("jacoco")
    id("com.google.protobuf") version "0.8.12"
    id("org.owasp.dependencycheck") version "6.0.5"
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

sourceSets{
    main {
      java {
        setSrcDirs(setOf("src/main/java", "src/generated/main/java", "src/generated/main/grpc"))
      }
      proto {
        setSrcDirs(setOf("src/main/proto"))
        setIncludes(setOf("**/*.proto"))
      }
    }
}

protobuf {
  generatedFilesBaseDir="$projectDir/src/generated"
  protoc {
    artifact="com.google.protobuf:protoc:3.0.0"
  }
  plugins {
    id("grpc") {
      artifact ="io.grpc:protoc-gen-grpc-java:1.29.0"
    }
  }
  
  generateProtoTasks {
    ofSourceSet("main").forEach { t ->
      t.plugins {
        id("grpc")
      }
    }
  }
}

dependencies {
    listOf(
            "spring-boot-starter-webflux",
            "spring-boot-starter-${project.ext["server.type"]}",
            "spring-boot-starter",
            "spring-boot-starter-actuator",
            "spring-boot-starter-aop"
    ).forEach { name ->
        implementation("org.springframework.boot:${name}") {
            exclude(module = "spring-boot-starter-logging")
        }
    }

    if (project.ext["server.type"] == "reactor-netty") {
        implementation("io.netty:netty-tcnative-boringssl-static:2.0.29.Final")
    }

    if (project.ext["server.type"] == "jetty") {
        listOf("jetty-alpn-server", "jetty-alpn-conscrypt-server").forEach { name ->
            implementation("org.eclipse.jetty:$name:9.4.27.v20200227")
        }
        implementation("org.eclipse.jetty.http2:http2-server:9.4.27.v20200227")
    }

    implementation("org.springframework.boot:spring-boot-starter-logging")
    implementation("net.logstash.logback:logstash-logback-encoder:6.6")

    implementation("io.micrometer:micrometer-registry-prometheus:1.6.3")
    implementation("io.projectreactor.addons:reactor-adapter:3.3.2.RELEASE")
    implementation("org.yaml:snakeyaml:1.26")
    implementation("io.r2dbc:r2dbc-spi:0.8.3.RELEASE")
    implementation("io.r2dbc:r2dbc-postgresql:0.8.3.RELEASE")
    implementation("io.r2dbc:r2dbc-h2:0.8.3.RELEASE")
    implementation("org.springframework.data:spring-data-r2dbc:1.2.3")
    implementation("io.github.skhatri:mounted-secrets-client:0.2.5")

    implementation("com.google.protobuf:protobuf-java:3.12.2")
    implementation("com.google.protobuf:protobuf-java-util:3.12.2")
    implementation("io.grpc:grpc-protobuf:${grpcVersion}")
    implementation("io.grpc:grpc-stub:${grpcVersion}")
    implementation("io.grpc:grpc-services:${grpcVersion}")

    //runtime
    implementation("io.grpc:grpc-netty-shaded:${grpcVersion}")
    //compile
    implementation("org.apache.tomcat:annotations-api:6.0.53")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(module = "junit")
        exclude(module = "mockito-core")
        exclude(module = "spring-boot-starter-logging")
    }
    testImplementation("io.projectreactor:reactor-test:3.3.2.RELEASE")
    testImplementation("org.mockito:mockito-core:3.3.3")

    testImplementation("org.junit.jupiter:junit-jupiter-api:$jupiterVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$jupiterVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$jupiterVersion")
    testRuntimeOnly("org.junit.vintage:junit-vintage-engine:$jupiterVersion")

    testImplementation("org.junit.platform:junit-platform-commons:$junitPlatformVersion")
    testImplementation("org.junit.platform:junit-platform-runner:$junitPlatformVersion")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:$junitPlatformVersion")
    testRuntimeOnly("org.junit.platform:junit-platform-engine:$junitPlatformVersion")
    testImplementation("org.testcontainers:testcontainers:1.15.1")
    testImplementation("org.testcontainers:junit-jupiter:1.15.1")
    testImplementation("org.testcontainers:postgresql:1.15.1")

}

tasks.test {
    useJUnitPlatform()
}

sonarqube {
    properties {
        property("sonar.projectName", "microservices-starter-java")
        property("sonar.host.url", "http://localhost:9000")
        property("sonar.projectKey", "microservices-starter-java-app")
        property("sonar.projectVersion", "${project.version}")
        property("sonar.junit.reportPaths", "${projectDir}/build/test-results/test")
        property("sonar.coverage.jacoco.xmlReportPaths", "${projectDir}/build/reports/jacoco/test/jacocoTestReport.xml")
        property("sonar.coverage.exclusions", "**/R.java")
    }
}

apply(from = "$rootDir/gradle/includes/codestyle.gradle.kts")
tasks.build {
    dependsOn(arrayOf("checkstyleMain", "checkstyleTest"))
}

tasks.jacocoTestReport {
    reports {
        xml.isEnabled = true
        csv.isEnabled = false
        html.destination = file("${buildDir}/jacocoHtml")
    }
}

tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            enabled = true
            limit {
                minimum = "0.2".toBigDecimal()
            }
        }

        rule {
            enabled = false
            element = "BUNDLE"
            includes = listOf("com.github.starter.*")
            excludes = listOf("**/Application*")
            limit {
                counter = "LINE"
                value = "COVEREDRATIO"
                minimum = "0.1".toBigDecimal()
            }
        }
    }
}

tasks.test {
    extensions.configure(JacocoTaskExtension::class) {
        destinationFile = file("$buildDir/jacoco/jacocoTest.exec")
        classDumpDir = file("$buildDir/jacoco/classpathdumps")
    }
}

tasks.test {
    finalizedBy("jacocoTestReport")
}

tasks.check {
    dependsOn(arrayOf("jacocoTestReport", "jacocoTestCoverageVerification"))
}

task("runApp", JavaExec::class) {
    main = "com.github.starter.Application"
    classpath = sourceSets["main"].runtimeClasspath
    jvmArgs = listOf(
            "-Xms512m", "-Xmx512m"
    )
}
