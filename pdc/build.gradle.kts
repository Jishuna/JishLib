plugins {
    id("java-library")
    id("maven-publish")
}

dependencies {
   api(project(":core"))
}

publishing {
    publications {
        create<MavenPublication>("PDC") {
            groupId = gradle.rootProject.group.toString()
            artifactId = gradle.rootProject.name + "-pdc"
            version = gradle.rootProject.version.toString()

            from(components["java"])
        }
    }
}