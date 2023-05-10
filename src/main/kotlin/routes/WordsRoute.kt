package routes

import dto.WordDraft
import exception.UserWithGivenEmailAlreadyExistsException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import services.WordServiceImpl
import utils.getDashedTenantId

fun Route.wordsRoute() {
    val wordService = WordServiceImpl()

    get("words/{user}/{language}/{translateLanguage}/{search}") {
        val user = call.parameters["user"].toString()
        val language = call.parameters["language"].toString()
        val translateLanguage = call.parameters["translateLanguage"].toString()
        val searchValue = call.parameters["search"].toString()
        val tenantId = getDashedTenantId(call.request.header("authorization")!!)

        val words = wordService.getAllWords(tenantId, user, language, translateLanguage)

        if (words == null) {
            call.respond(
                HttpStatusCode.NotFound,
                "First you should add new words"
            )
        } else {
            if (searchValue == "null") {
                call.respond(words)
            } else{
                val searchedWords = wordService.searchWords(tenantId, user, language, translateLanguage, searchValue)
                call.respond(searchedWords)
            }
        }
    }

    get("words/random/{user}/{language}/{translateLanguage}") {
        val user = call.parameters["user"].toString()
        val language = call.parameters["language"].toString()
        val translateLanguage = call.parameters["translateLanguage"].toString()
        val tenantId = getDashedTenantId(call.request.header("authorization")!!)


        val word = wordService.getWord(tenantId, user, language, translateLanguage)

        if (word == null) {
            call.respond(
                HttpStatusCode.NotFound,
                "First you should add new words"
            )
        } else {
            call.respond(word)
        }
    }

    post ("words"){
        val newWord = call.receive<WordDraft>()
        val tenantId = getDashedTenantId(call.request.header("authorization")!!)

        try {
            call.respond(wordService.addWord(tenantId, newWord))
        } catch (e: UserWithGivenEmailAlreadyExistsException) {
            call.respond(HttpStatusCode.Conflict, e.message.toString())
        }
    }
}