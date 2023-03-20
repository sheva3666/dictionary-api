package com.example.plugins

import routes.usersRoute
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {

    routing {
        usersRoute()
    }
}
