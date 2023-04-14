package routes

import dao.NativeWordDao
import dao.NativeWordDaoImpl
import dao.TranslatedWordDao
import dto.NativeWordDraft
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.englishWordsRoute(translatedWordsRepository: TranslatedWordDao) {
    val nativeWordsRepository: NativeWordDao = NativeWordDaoImpl()


    get("native/{user}/{language}/{translateLanguage}") {
        val user = call.parameters["user"].toString()
        val language = call.parameters["language"].toString()
        val translateLanguage = call.parameters["translateLanguage"].toString()


        val englishWords = nativeWordsRepository.getAllNativeWords(user, language, translateLanguage)

        if (englishWords == null) {
            call.respond(
                HttpStatusCode.NotFound,
                "First you should add new words"
            )
        } else {
            call.respond(englishWords)
        }
    }

    get("native/random/{user}/{language}/{translateLanguage}") {
        val user = call.parameters["user"].toString()
        val language = call.parameters["language"].toString()
        val translateLanguage = call.parameters["translateLanguage"].toString()

        val englishWords = nativeWordsRepository.getNativeWord(user, language, translateLanguage)

        if (englishWords == null) {
            call.respond(
                HttpStatusCode.NotFound,
                "First you should add new words"
            )
        } else {
            call.respond(englishWords)
        }
    }
    post ("native"){
        val englishWordDraft = call.receive<NativeWordDraft>()
        val wordIsInList = nativeWordsRepository.checkNativeWord(englishWordDraft.word)

        if (wordIsInList == null) {
            val word = nativeWordsRepository.addNativeWord(englishWordDraft)
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