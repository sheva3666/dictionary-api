package com.example.plugins

import dao.AuthDao
import dao.AuthDaoImpl
import dao.TranslatedWordDao
import dao.TranslatedWordDaoImpl
import io.ktor.server.application.*
import io.ktor.server.routing.*
import routes.authRoute
import routes.wordsRoute
import routes.translatedWordsRoute
import routes.usersRoute

fun Application.configureRouting() {
    val translatedWordsRepository: TranslatedWordDao = TranslatedWordDaoImpl()
    val authRepository: AuthDao = AuthDaoImpl()

    routing {
        usersRoute(authRepository)
        wordsRoute(translatedWordsRepository)
        translatedWordsRoute(translatedWordsRepository)
        authRoute(authRepository)
    }
}
