plugins {
    java
    id("net.minecrell.plugin-yml.bukkit") version "0.6.1-SNAPSHOT"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.18.2-R0.1-SNAPSHOT")
    implementation(project(":sunburst-api"))

    library("org.projectlombok:lombok:1.18.22")
    annotationProcessor("org.projectlombok:lombok:1.18.22")

    library("org.json:json:20220320")
    library("org.apache.httpcomponents:httpclient:4.5.13")
}

bukkit {
    name = "Sunburst"
    version = project.version as String
    main = "dev.plex.Sunburst"
}

tasks {
    build {
        dependsOn(shadowJar)
    }
}
tasks.getByName<Jar>("shadowJar") {
    archiveBaseName.set("Sunburst")
    archiveClassifier.set("")
}

tasks.register<Wrapper>("wrapper")
{
    gradleVersion = "7.4.2"
}
tasks.register("prepareKotlinBuildScriptModel"){}