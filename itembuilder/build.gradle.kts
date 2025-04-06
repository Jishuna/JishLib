plugins {
    id("java-library")
    id("maven-publish")
}

dependencies {
    api(project(":core"))
}

publishing {
    publications {
        create<MavenPublication>("ItemBuilder") {
            groupId = gradle.rootProject.group.toString()
            artifactId = gradle.rootProject.name + "-itembuilder"
            version = gradle.rootProject.version.toString()

            from(components["java"])
        }
    }
}