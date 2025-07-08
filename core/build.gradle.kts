plugins {
    id("java-library")
    id("maven-publish")
}

dependencies {
    compileOnlyApi("org.spigotmc:spigot-api:1.21.3-R0.1-SNAPSHOT")
    compileOnlyApi("org.jetbrains:annotations:24.0.0")
    api("me.jishuna:genericdataapi-yml:1.0.0-SNAPSHOT")
    api("net.kyori:adventure-text-minimessage:4.17.0")
    api("net.kyori:adventure-platform-bukkit:4.3.4")
}

publishing {
    publications {
        create<MavenPublication>("Core") {
            groupId = gradle.rootProject.group.toString()
            artifactId = gradle.rootProject.name + "-core"
            version = gradle.rootProject.version.toString()

            from(components["java"])
        }
    }
}