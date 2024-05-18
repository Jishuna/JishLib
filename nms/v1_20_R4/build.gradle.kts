plugins {
    id("java")
    id("io.papermc.paperweight.userdev") version "1.6.3"
}

dependencies {
    compileOnly(project(":core"))
    paperweight.paperDevBundle("1.20.5-R0.1-SNAPSHOT")
	compileOnly("net.kyori:adventure-platform-bukkit:4.3.3-SNAPSHOT")
	compileOnly("net.kyori:adventure-text-minimessage:4.17.0")
}