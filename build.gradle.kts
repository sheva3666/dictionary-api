import org.jooq.meta.jaxb.Logging

val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

val dbUrl = "jdbc:postgresql://localhost:5432/postgres"
val dbUser = "admin"
val dbPassword = "admin_password"
val dbSchema = "dictionary"

val postgresVersion = "42.5.1"
val jooqVersion = "3.15.3"

plugins {
    kotlin("jvm") version "1.8.10"
    application
    id("io.ktor.plugin") version "2.2.4"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.0"
    id("org.flywaydb.flyway") version "9.11.0"
    id("nu.studer.jooq") version "6.0.1"
}

group = "com.example"
version = "0.0.1"
application {
    mainClass.set("com.example.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
    implementation("io.ktor:ktor-server-cors:$ktor_version")
    implementation("org.flywaydb:flyway-core:9.11.0")
    implementation("org.postgresql:postgresql:$postgresVersion")
    jooqGenerator("org.postgresql:postgresql:$postgresVersion")
    api("org.jooq:jooq:$jooqVersion")
    implementation("com.zaxxer:HikariCP:5.0.1")
    //1. (JWT Authentication) adding dependencies for Authentication handling
    implementation("io.ktor:ktor-server-auth:$ktor_version")
    implementation("io.ktor:ktor-server-auth-jwt:$ktor_version")
}

flyway {
    url = dbUrl
    schemas = arrayOf(dbSchema)
    user = dbUser
    password = dbPassword
    baselineVersion = "-1"
    table = "flyway_schema_history"
    cleanDisabled = false
    // The file name prefix for sql migrations (default: "V")
    sqlMigrationPrefix = "V"
    // The file name separator for sql migrations (default = "__")
    sqlMigrationSeparator = "__"
    // The file name suffix for sql migrations (default = ".sql")
    sqlMigrationSuffixes = arrayOf(".sql")
    // Locations to scan recursively for migrations. (default = db/migration)
    // locations = arrayOf("db/migration")
    outOfOrder = false
    // Whether to automatically call baseline when migrate is executed against a non-empty schema with no metadata table. (default = false)
    // Be careful when enabling this as it removes the safety net that ensures Flyway does not migrate the wrong database in case of a configuration mistake!
    baselineOnMigrate = true
}

jooq {
    version.set(jooqVersion)
    edition.set(nu.studer.gradle.jooq.JooqEdition.OSS)

    configurations {
        create("main") {
            generateSchemaSourceOnCompilation.set(true)

            jooqConfiguration.apply {
                logging = Logging.INFO
                jdbc.apply {
                    driver = "org.postgresql.Driver"
                    url = dbUrl
                    user = dbUser
                    password = dbPassword
                }
                generator.apply {
                    name = "org.jooq.codegen.KotlinGenerator"
                    strategy.name = "org.jooq.codegen.DefaultGeneratorStrategy"
                    database.apply {
                        name = "org.jooq.meta.postgres.PostgresDatabase"
                        inputSchema = dbSchema
                        excludes = "key_vault|flyway_schema_history"
                    }
                    generate.apply {
                        isDeprecated = false
                        isRecords = true
                        isRelations = true
                        isPojos = true
                        isPojosEqualsAndHashCode = true
                    }
                    target.apply {
                        packageName = "jooq.generated"
                    }
                }
            }
        }
    }
}