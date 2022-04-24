plugins {
    java
    `maven-publish`
}

allprojects {
    group = "dev.plex"
    version = "0.1"
}
subprojects {
    apply(plugin = "java")
    apply(plugin = "maven-publish")
    repositories {
        mavenCentral()
        maven {
            url = uri("https://papermc.io/repo/repository/maven-public/")
        }
        maven {
            url = uri("https://oss.sonatype.org/content/groups/public/")
        }
    }

    tasks {
        compileJava {
            options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything

            // Set the release flag. This configures what version bytecode the compiler will emit, as well as what JDK APIs are usable.
            // See https://openjdk.java.net/jeps/247 for more information.
            options.release.set(17)
        }
        javadoc {
            options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything
        }
        processResources {
            filteringCharset = Charsets.UTF_8.name() // We want UTF-8 for everything
        }
    }

}
var copyJars = tasks.register<Copy>("copyJars") {
    from(subprojects.filter {
         it.tasks.findByName("shadowJar") != null
    }.map {
        it.tasks.getByName<Jar>("shadowJar")
    })
    into(file("build/libs"))
}

tasks {
    build {
        dependsOn(copyJars)
    }
    jar {
        enabled = false
    }
}