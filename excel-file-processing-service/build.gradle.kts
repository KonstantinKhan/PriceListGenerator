plugins {
    kotlin("jvm") version "1.9.21"
}

group = "com.khan366kos"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")

    // https://mvnrepository.com/artifact/org.apache.poi/poi-ooxml
    implementation("org.apache.poi:poi-ooxml:5.2.5")

}

tasks.test {
    useJUnitPlatform()
}