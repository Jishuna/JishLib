plugins {
    id("java")
    id("io.papermc.paperweight.userdev") version "1.7.1"
}

dependencies {
    compileOnly(project(":core"))
    paperweight.paperDevBundle("1.21-R0.1-SNAPSHOT")
	compileOnly("net.kyori:adventure-platform-bukkit:4.3.3")
	compileOnly("net.kyori:adventure-text-minimessage:4.17.0")
}