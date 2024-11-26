plugins {
    id("java-library")
    id("com.gradleup.shadow") version "8.3.5"
    id("maven-publish")
    id("io.papermc.paperweight.userdev") version "1.7.5" apply false
}

dependencies {
    shadow(project(":core"))
    project.project(":nms").subprojects.forEach {
        implementation(project(path = it.path, configuration = "reobf"))
    }
}

subprojects {
    apply(plugin = "java-library")

    dependencies {
        compileOnly(project(":core"))
    }
}

tasks.shadowJar {
    archiveClassifier = ""
    archiveVersion = ""
}

publishing {
    publications {
        create<MavenPublication>("NMS") {
            groupId = gradle.rootProject.group.toString()
            artifactId = gradle.rootProject.name + "-nms"
            version = gradle.rootProject.version.toString()

            from(components["shadow"])
        }
    }
}