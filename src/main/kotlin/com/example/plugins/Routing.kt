package com.example.plugins

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import routes.authRoute
import routes.translatedWordsRoute
import routes.usersRoute
import routes.wordsRoute

fun Application.configureRouting() {

    routing {
        authenticate {
            usersRoute()
            wordsRoute()
            translatedWordsRoute()
            authRoute()
        }
    }
}
