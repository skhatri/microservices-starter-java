tasks.wrapper {
    gradleVersion = "6.4.1"
}

allprojects {
    apply(plugin = "idea")
    apply(plugin = "java")

    repositories {
        mavenCentral()
        maven {
            url = uri("https://oss.sonatype.org/content/repositories/snapshots")
        }
    }

}

