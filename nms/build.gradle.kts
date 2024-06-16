plugins {
    id("java")
	id("io.github.goooler.shadow") version "8.1.7"
    id("io.papermc.paperweight.userdev") version "1.7.1" apply false
}

val adapters = configurations.create("adapters") {
    isCanBeConsumed = false
    isCanBeResolved = true
    shouldResolveConsistentlyWith(configurations["runtimeClasspath"])
}

dependencies {
      project.project(":nms").subprojects.forEach {
		"adapters"(project(path = it.path, configuration = "reobf"))
    }
}

tasks.shadowJar {
	configurations.add(adapters)
	
	project.project(":nms").subprojects.forEach {
        dependencies {
            include(dependency("${it.group}:${it.name}"))
        }
    }
}

tasks.assemble {
	dependsOn(tasks.shadowJar)
}