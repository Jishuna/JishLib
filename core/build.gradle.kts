plugins {
    id("java")
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.20.6-R0.1-SNAPSHOT")
	implementation("net.kyori:adventure-text-minimessage:4.17.0")
	implementation("net.kyori:adventure-text-serializer-legacy:4.17.0")
	implementation("net.kyori:adventure-text-serializer-gson:4.17.0")
}