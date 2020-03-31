tasks.wrapper {
    gradleVersion = "6.2.2"
}

allprojects {
    apply(plugin = "idea")
    apply(plugin = "java")

    repositories {
        mavenCentral()
    }

}

