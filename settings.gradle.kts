rootProject.name = "Jishlib"

gradle.rootProject {
    this.version = "3.0.0-SNAPSHOT"
    this.group = "me.jishuna"
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        mavenLocal()
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
        maven("https://oss.sonatype.org/content/repositories/central")
        maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    }
}

include("core")
include("nms")
include("nms:v1_20_R4")
findProject(":nms:v1_20_R4")?.name = "v1_20_R4"