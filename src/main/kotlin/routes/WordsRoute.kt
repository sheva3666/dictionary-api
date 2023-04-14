package routes

import dao.TranslatedWordDao
import dao.WordDao
import dao.WordDaoImpl
import dto.WordDraft
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.wordsRoute(translatedWordsRepository: TranslatedWordDao) {
    val wordsRepository: WordDao = WordDaoImpl()


    get("words/{user}/{language}/{translateLanguage}") {
        val user = call.parameters["user"].toString()
        val language = call.parameters["language"].toString()
        val translateLanguage = call.parameters["translateLanguage"].toString()


        val words = wordsRepository.getAllWords(user, language, translateLanguage)

        if (words == null) {
            call.respond(
                HttpStatusCode.NotFound,
                "First you should add new words"
            )
        } else {
            call.respond(words)
        }
    }

    get("words/random/{user}/{language}/{translateLanguage}") {
        val user = call.parameters["user"].toString()
        val language = call.parameters["language"].toString()
        val translateLanguage = call.parameters["translateLanguage"].toString()

        val words = wordsRepository.getWord(user, language, translateLanguage)

        if (words == null) {
            call.respond(
                HttpStatusCode.NotFound,
                "First you should add new words"
            )
        } else {
            call.respond(words)
        }
    }
    post ("words"){
        val wordDraft = call.receive<WordDraft>()
        val wordIsInList = wordsRepository.checkWord(wordDraft.word)

        if (wordIsInList == null) {
            val word = wordsRepository.addWord(wordDraft)
            translatedWordsRepository.addTranslatedWord(wordDraft)
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