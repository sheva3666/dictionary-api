package routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import services.TranslatedWordsServiceImpl
import utils.getDashedTenantId

fun Route.translatedWordsRoute() {
    val translatedWordsService = TranslatedWordsServiceImpl()

    get("translate/{userEmail}/{languageForLearn}/{language}") {
        val language = call.parameters["language"].toString()
        val languageForLearn = call.parameters["languageForLearn"].toString()
        val userEmail = call.parameters["userEmail"].toString()
        val tenantId = getDashedTenantId(call.request.header("authorization")!!)

        val translatedWords = translatedWordsService.getRandomTranslatedWords(tenantId, userEmail, languageForLearn, language)

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