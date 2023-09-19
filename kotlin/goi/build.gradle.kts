val antlr_version: String by project
val kotest_version: String by project
val kotlin_coroutines_version: String by project
val logback_version: String by project

plugins {
    kotlin("jvm") version "1.9.0"
    application
    antlr
}

group = "net.paploo"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    antlr("org.antlr:antlr4:$antlr_version")
//    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlin_coroutines_version")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("ch.qos.logback:logback-classic:$logback_version")

    testImplementation("io.kotest:kotest-runner-junit5:$kotest_version")
    testImplementation("io.kotest:kotest-assertions-core:$kotest_version")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

application {
    mainClass.set("MainKt")
}



// ANTLR

tasks.generateGrammarSource {
    //The default is project_dir/build/generated-src/antlr/main, but this moves
    //it into the src directory like we do with jOOQ.
    outputDirectory = File(project.projectDir, "src/main/java/net/paploo/goi/gen/antlr")
}

tasks.generateTestGrammarSource {
    outputDirectory = File(project.projectDir, "src/test/java/net/paploo/goi/gen/antlr")
}

tasks.compileKotlin {
    dependsOn("generateGrammarSource")
}

tasks.compileTestKotlin {
    dependsOn("generateTestGrammarSource")
}