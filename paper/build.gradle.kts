plugins {
    id("java")
}

dependencies {
	compileOnly(project(":core"))
    compileOnly("io.papermc.paper:paper-api:1.20.6-R0.1-SNAPSHOT")
}