package com.example.plugins

import dao.TranslatedWordDao
import dao.TranslatedWordDaoImpl
import io.ktor.server.application.*
import io.ktor.server.routing.*
import routes.englishWordsRoute
import routes.translatedWordsRoute
import routes.usersRoute

fun Application.configureRouting() {
    val translatedWordsRepository: TranslatedWordDao = TranslatedWordDaoImpl()

    routing {
        usersRoute()
        englishWordsRoute(translatedWordsRepository)
        translatedWordsRoute(translatedWordsRepository)
    }
}
