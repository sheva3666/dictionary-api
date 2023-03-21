package com.example.plugins

import io.ktor.server.application.*
import io.ktor.server.routing.*
import routes.englishWordsRoute
import routes.usersRoute

fun Application.configureRouting() {

    routing {
        usersRoute()
        englishWordsRoute()
    }
}
