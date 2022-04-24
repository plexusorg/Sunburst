plugins {
    java
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.18.2-R0.1-SNAPSHOT")
}

tasks {
    build {
        dependsOn(shadowJar)
    }
}
tasks.getByName<Jar>("shadowJar") {
    archiveBaseName.set("Sunburst")
    archiveClassifier.set("API")
}

tasks.register<Wrapper>("wrapper")
{
    gradleVersion = "7.4.2"
}
tasks.register("prepareKotlinBuildScriptModel"){}