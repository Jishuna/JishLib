rootProject.name = "jishlib"

gradle.rootProject {
    this.version = "4.0.0-SNAPSHOT"
    this.group = "me.jishuna"
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
        maven("https://oss.sonatype.org/content/repositories/central")
        mavenLocal()
    }
}

include("core")
include("inventory")
include("pdc")
include("nms")
include("nms:v1_21_R3")
findProject(":nms:v1_21_R3")?.name = "v1_21_R3"