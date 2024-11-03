plugins {
    id("java")
    id("application")
}

group = "no.nav"
version = "1.0"

repositories {
    mavenCentral()
}

application {
    mainClass.set("no.nav.Main") // Sett til fullstendig pakke- og klassenavn til Main-klassen
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.11.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.json:json:20240303")
    implementation("io.github.cdimascio:dotenv-java:3.0.2")
}

tasks.test {
    useJUnitPlatform()
}