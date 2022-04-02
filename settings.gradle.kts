rootProject.name="starter-java"

listOf("app", "load-testing").forEach { folder ->
    include(folder)
    project(":${folder}").projectDir = file(folder)
}

pluginManagement {
	repositories {
		maven { url=uri("https://repo.spring.io/release") }
		mavenCentral()
		gradlePluginPortal()
	}
}
