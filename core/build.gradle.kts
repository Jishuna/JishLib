plugins {
    id("java")
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.20.6-R0.1-SNAPSHOT")
	implementation(project(":paper"))
	implementation("net.kyori:adventure-platform-bukkit:4.3.3-SNAPSHOT")
	implementation("net.kyori:adventure-text-minimessage:4.17.0")
}