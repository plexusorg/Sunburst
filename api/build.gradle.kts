plugins {
    java
    `maven-publish`
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.18.2-R0.1-SNAPSHOT")
}

tasks {
    build {
        dependsOn(publishToMavenLocal)
    }
}
tasks.getByName<Jar>("jar") {
    archiveBaseName.set("Sunburst")
    archiveClassifier.set("")
}

tasks.register<Wrapper>("wrapper")
{
    gradleVersion = "7.4.2"
}
tasks.register("prepareKotlinBuildScriptModel"){}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group as String
            version = project.version as String
            artifactId = "sunburst-api"
            from(components["java"])
        }
    }
}