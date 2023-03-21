package com.example

import com.example.plugins.configureRouting
import com.example.plugins.configureSerialization
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.cors.routing.*

fun main() {
    embeddedServer(Netty, port = 8082, host = "localhost", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    install(CORS) {
        allowHost("*")
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Get)
        allowHeader(HttpHeaders.ContentType)
    }
    configureSerialization()
    configureRouting()
}
