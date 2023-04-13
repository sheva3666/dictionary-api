package routes

import dao.TranslatedWordDao
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.translatedWordsRoute(translatedWordsRepository: TranslatedWordDao) {
//    val translatedWordsRepository: TranslatedWordDao = TranslatedWordDaoImpl()


    get("translate/{user}/{language}") {
        val user = call.parameters["user"].toString()
        val language = call.parameters["language"].toString()

        val translatedWords = translatedWordsRepository.getAllTranslatedWords(user, language)

        if (translatedWords == null) {
            call.respond(
                HttpStatusCode.NotFound,
                "First you should add new words"
            )
        } else {
            call.respond(translatedWords)
        }
    }

    get("translate/{language}") {
        val language = call.parameters["language"].toString()

        val translatedWords = translatedWordsRepository.getRandomTranslatedWords(language)

        if (translatedWords == null) {
            call.respond(
                HttpStatusCode.NotFound,
                "First you should add new words"
            )
        } else {
            call.respond(translatedWords)
        }
    }
}