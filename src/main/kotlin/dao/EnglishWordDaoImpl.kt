package dao

import dto.EnglishWord
import dto.EnglishWordDraft
import repository.EnglishWordDao

class EnglishWordDaoImpl: EnglishWordDao {
    private val englishWords = mutableListOf<EnglishWord>(
        EnglishWord("mother-id", "mother-translate", "admin", "mother", ),

    )
    override fun getAllEnglishWords(user: String): List<EnglishWord> {
        return englishWords.filter {it.user == user}
    }

    override fun getEnglishWord(id: String, user: String, language: String): EnglishWord? {
        TODO("Not yet implemented")
    }

    override fun addEnglishWord(draft: EnglishWordDraft): EnglishWord {
        TODO("Not yet implemented")
    }

    override fun checkEnglishWord(word: String): EnglishWord? {
        return englishWords.firstOrNull {it.word == word}
    }
}