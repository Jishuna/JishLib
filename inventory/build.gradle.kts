plugins {
    id("java-library")
    id("maven-publish")
}

dependencies {
    api(project(":core"))
}

publishing {
    publications {
        create<MavenPublication>("Inventory") {
            groupId = gradle.rootProject.group.toString()
            artifactId = gradle.rootProject.name + "-inventory"
            version = gradle.rootProject.version.toString()

            from(components["java"])
        }
    }
}