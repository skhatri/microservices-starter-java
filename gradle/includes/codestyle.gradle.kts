buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("com.puppycrawl.tools:checkstyle:8.30")
    }
}
apply(plugin = "checkstyle")

tasks.withType<Checkstyle>().configureEach {
    configFile = file("$rootDir/gradle/settings/checkstyle.xml")
    reports {
        xml.isEnabled = false
        html.isEnabled = true
        html.stylesheet = resources.text.fromFile("$rootDir/gradle/settings/checkstyle.xsl")
    }
}

