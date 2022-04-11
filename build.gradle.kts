plugins {
    java
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("fr.stardustenterprises.rust.importer") version "3.2.0"
    id("xyz.jpenilla.run-paper") version "1.0.6"
}

group = "io.denery.rustplugin"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
}

dependencies {
    rust(project(":rust-library"))
    compileOnly("io.papermc.paper:paper-api:1.18.2-R0.1-SNAPSHOT")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

rustImport {
    baseDir.set("/META-INF/natives")
    layout.set("hierarchical")
}

tasks {
    getByName<Test>("test") {
        useJUnitPlatform()
    }
    runServer {
        runDirectory(file("$rootDir/run"))
        minecraftVersion("1.18.2")
    }
    shadowJar {
        archiveClassifier.set("")
    }
}