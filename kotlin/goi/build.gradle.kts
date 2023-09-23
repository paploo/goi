import com.adarshr.gradle.testlogger.theme.ThemeType
import org.jooq.meta.jaxb.SchemaMappingType

val antlr_version: String by project
val commons_csv_version: String by project
val kotest_version: String by project
val kotlin_coroutines_version: String by project
val logback_version: String by project
val hikari_version: String by project
val postgres_version: String by project
val jooq_version: String by project

plugins {
    kotlin("jvm") version "1.9.0"
    application
    antlr
    id("nu.studer.jooq") version "8.2.1"
    id("com.adarshr.test-logger") version "3.2.0"
}

group = "net.paploo"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    antlr("org.antlr:antlr4:$antlr_version")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlin_coroutines_version")

    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("org.apache.commons:commons-csv:$commons_csv_version")

    implementation("com.zaxxer:HikariCP:$hikari_version")
    implementation("org.postgresql:postgresql:$postgres_version")

    implementation("org.jooq:jooq:$jooq_version")
    implementation("org.jooq:jooq-meta:$jooq_version")
    implementation("org.jooq:jooq-codegen:$jooq_version")

    jooqGenerator("org.postgresql:postgresql:$postgres_version")

    testImplementation("io.kotest:kotest-runner-junit5:$kotest_version")
    testImplementation("io.kotest:kotest-assertions-core:$kotest_version")
}

tasks.test {
    useJUnitPlatform()
    testlogger {
        theme = ThemeType.STANDARD_PARALLEL
    }
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


// JOOQ
jooq {
    version = jooq_version

    configurations {
        create("main") {
            generateSchemaSourceOnCompilation = false

            jooqConfiguration.apply {
                logging = org.jooq.meta.jaxb.Logging.INFO

                jdbc.apply {
                    driver = "org.postgresql.Driver"
                    url = "jdbc:postgresql://localhost:5432/goi"
                    user = "postgres"
                    password = "postgres"
                }

                generator.apply {
                    name = "org.jooq.codegen.KotlinGenerator"
                    strategy.name = "org.jooq.codegen.DefaultGeneratorStrategy"
                    database.apply {
                        name = "org.jooq.meta.postgres.PostgresDatabase"
                        schemata.addAll(
                            arrayOf(
                                "grammar", "kanji", "source", "vocabulary"
                            ).map {
                                SchemaMappingType().apply { inputSchema = it }
                            }
                        )
                        includes = listOf(
                            "grammar.example",
                            "grammar.example_reference",
                            "grammar.rule",
                            "grammar.rule_reference",

                            "kanji.kanji_character",

                            "source.lesson",
                            "source.source",

                            "vocabulary.conjugation",
                            "vocabulary.conjugation_set",
                            "vocabulary.definition",
                            "vocabulary.linkages",
                            "vocabulary.reference",
                            "vocabulary.spelling",
                            "vocabulary.vocabulary",
                        ).joinToString("|")
                    }
                    generate.apply {
                        isFluentSetters = true
                    }
                    target.apply {
                        packageName = "net.paploo.goi.persistence.db.goi"
                        directory = "${project.projectDir}/src/gen/kotlin"
                    }
                }
            }
        }
    }
}