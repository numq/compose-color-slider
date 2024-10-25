import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm") version "2.0.0"
    id("org.jetbrains.compose") version "1.6.10"
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.0"
    id("maven-publish")
}

group = "com.github.numq"
version = "1.0.0"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation(project(":library"))
}

kotlin {
    jvmToolchain(17)
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "compose-color-slider"
            packageVersion = "1.0.0"
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            group = "com.github.numq"
            version = "1.0.0"
        }
    }
}