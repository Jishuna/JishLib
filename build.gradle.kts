plugins {
    id("java-library")
    id("com.gradleup.shadow") version "8.3.5"
    id("maven-publish")
}

configurations.all {
    resolutionStrategy.cacheChangingModulesFor(0, "seconds")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

dependencies {
    api(project(":core"))
}

tasks.shadowJar {
    archiveClassifier = ""
    archiveVersion = ""
}

publishing {
    publications {
        create<MavenPublication>("All") {
            groupId = gradle.rootProject.group.toString()
            artifactId = gradle.rootProject.name
            version = gradle.rootProject.version.toString()

            from(components["shadow"])
        }
    }
}