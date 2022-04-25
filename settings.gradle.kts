pluginManagement {
    repositories {
        maven { url = uri("https://nexus.telesphoreo.me/repository/gradle-plugins/") }
        gradlePluginPortal()
    }
}

rootProject.name = "sunburst"
include(":server")
project(":server").name = "sunburst-server"
include(":api")
project(":api").name = "sunburst-api"
