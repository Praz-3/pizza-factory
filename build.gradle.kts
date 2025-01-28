plugins {
    kotlin("jvm") version "2.1.0"
    application
}

group = "com.pizzafactory"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

application {
    mainClass.set("com.pizzafactory.PizzaFactoryConsoleKt")
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "com.pizzafactory.PizzaFactoryConsoleKt"
    }
    archiveBaseName.set("pizza-factory")
    archiveVersion.set("1.0.0")
}
