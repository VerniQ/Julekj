plugins {
    id("java")
    id("org.beryx.jlink") version "3.0.1"
    id("application")
}

group = "me.verni"
version = "0.0.1"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.postgresql:postgresql:42.7.2")
    implementation("com.h2database:h2:2.2.222")
    implementation("org.hibernate:hibernate-core:6.2.6.Final")

    implementation("jakarta.validation:jakarta.validation-api:3.0.2")
    implementation("org.hibernate.validator:hibernate-validator:8.0.1.Final")

    implementation("org.openjfx:javafx-controls:23.0.1:win")
    implementation("org.openjfx:javafx-fxml:23.0.1:win")
    implementation("org.openjfx:javafx-graphics:23.0.1:win")
    implementation("org.openjfx:javafx-base:23.0.1:win")
}

application {
    mainClass.set("me.juleK.Main")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}