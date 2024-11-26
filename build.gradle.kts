plugins {
    id("java-library")
    id("com.gradleup.shadow") version "8.3.5"
    id("maven-publish")
}

dependencies {
  api(project(":core"))
  api(project(":inventory"))
  api(project(path = ":nms", configuration = "shadow"))
}

tasks.shadowJar {
    archiveClassifier = ""
    archiveVersion = ""
}

publishing {
    publications {
        create<MavenPublication>("All") {
            groupId = gradle.rootProject.group.toString()
            artifactId = gradle.rootProject.name + "-all"
            version = gradle.rootProject.version.toString()

            from(components["shadow"])
        }
    }
}