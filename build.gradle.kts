import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.21"
    id("org.openjfx.javafxplugin") version "0.0.13"
    application
}

group = "com.marcfearby"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

javafx {
    version = "12.0.1"
    modules(
        "javafx.base",
        "javafx.controls",
        "javafx.graphics"
    )
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("no.tornado:tornadofx:1.7.20")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

application {
    mainClass.set("MainKt")
}