package routes

import dao.EnglishWordDaoImpl
import dto.EnglishWordDraft
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import repository.EnglishWordDao

fun Route.englishWordsRoute() {
    val englishWordsRepository: EnglishWordDao = EnglishWordDaoImpl()


    get("english/{user}") {
        val user = call.parameters["user"].toString()

        val englishWords = englishWordsRepository.getAllEnglishWords(user)

        if (user == null) {
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
            call.respond(
                HttpStatusCode.OK,
                "word ${word.word} added to your list"
            )
        } else {
            call.respond(
                HttpStatusCode.OK,
                "Something went wrong"
            )
        }
    }
}