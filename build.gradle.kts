val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

val dbUrl = "jdbc:postgresql://localhost:5432/postgres"
val dbUser = "admin"
val dbPassword = "admin_password"
val dbSchema = "dictionary"

val postgresVersion = "42.5.1"

plugins {
    kotlin("jvm") version "1.8.10"
    application
    id("io.ktor.plugin") version "2.2.4"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.0"
    id("org.flywaydb.flyway") version "9.11.0"
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