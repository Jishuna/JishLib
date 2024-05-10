plugins {
    id("java")
    id("maven-publish")
	id("io.github.goooler.shadow") version "8.1.7"
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

dependencies {
	implementation(project(":core"))
	implementation(project(path = ":nms", configuration = "shadow"))	
}

tasks.shadowJar {
	archiveClassifier.set("")
    archiveVersion.set("")
}

tasks.assemble {
	dependsOn(tasks.shadowJar)
}

publishing {
    publications {
        create<MavenPublication>("Maven") {
            project.shadow.component(this)
        }
    }

    repositories {
        maven("https://repo.epicebic.xyz/public/") {
            credentials {
                this.username = "josh"
                this.password = System.getenv("Secret")
            }
        }
    }
}
