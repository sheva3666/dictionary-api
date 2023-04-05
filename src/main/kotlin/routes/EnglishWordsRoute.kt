package routes

import dao.EnglishWordDao
import dao.EnglishWordDaoImpl
import dao.TranslatedWordDao
import dto.EnglishWordDraft
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.englishWordsRoute(translatedWordsRepository: TranslatedWordDao) {
    val englishWordsRepository: EnglishWordDao = EnglishWordDaoImpl()


    get("english/{user}/{language}/{translateLanguage}") {
        val user = call.parameters["user"].toString()
        val language = call.parameters["language"].toString()
        val translateLanguage = call.parameters["translateLanguage"].toString()


        val englishWords = englishWordsRepository.getAllEnglishWords(user, language, translateLanguage)

        if (englishWords == null) {
            call.respond(
                HttpStatusCode.NotFound,
                "First you should add new words"
            )
        } else {
            call.respond(englishWords)
        }
    }

    get("english/random/{user}/{language}/{translateLanguage}") {
        val user = call.parameters["user"].toString()
        val language = call.parameters["language"].toString()
        val translateLanguage = call.parameters["translateLanguage"].toString()

        val englishWords = englishWordsRepository.getEnglishWord(user, language, translateLanguage)

        if (englishWords == null) {
            call.respond(
                HttpStatusCode.NotFound,
                "First you should add new words"
            )
        } else {
            call.respond(englishWords)
        }
    }
    post ("english"){
        val englishWordDraft = call.receive<EnglishWordDraft>()
        val wordIsInList = englishWordsRepository.checkEnglishWord(englishWordDraft.word)

        if (wordIsInList == null) {
            val word = englishWordsRepository.addEnglishWord(englishWordDraft)
            translatedWordsRepository.addTranslatedWord(englishWordDraft)
            call.respond(
                HttpStatusCode.OK,
                "word ${word.word} added to your list"
            )
        } else {
            call.respond(
                HttpStatusCode.OK,
                "This word already on your list."
            )
        }
    }
}