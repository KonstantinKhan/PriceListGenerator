plugins {
    kotlin("jvm") apply false
}

group = "com.khan366kos"
version = "1.0-SNAPSHOT"

subprojects {
    group = rootProject.group
    version = rootProject.version
    repositories {
        mavenCentral()
    }
}