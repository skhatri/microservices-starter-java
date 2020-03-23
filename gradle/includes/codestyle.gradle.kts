buildscript {
    apply(plugin = "checkstyle")
}

tasks.withType<Checkstyle>().configureEach {
    configFile = file("$rootDir/gradle/settings/checkstyle.xml")
    reports {
        xml.isEnabled = false
        html.isEnabled = true
        html.stylesheet = resources.text.fromFile("$rootDir/gradle/settings/checkstyle.xsl")
    }
}