package dao

import dto.EnglishWord
import dto.EnglishWordDraft
import repository.EnglishWordDao

class EnglishWordDaoImpl: EnglishWordDao {
    private val englishWords = mutableListOf<EnglishWord>(
        EnglishWord("mother-id", "mother-translate", "admin", "mother", "мама", "ukrainian", "english"),
        EnglishWord("father-id", "father-translate", "admin", "father", "тато", "ukrainian", "english" ),

    )
    override fun getAllEnglishWords(user: String, language: String, translateLanguage: String): List<EnglishWord> {
        return englishWords.filter {it.user == user && it.language == language}
    }

    override fun getEnglishWord( user: String, language: String, translateLanguage: String): EnglishWord {
        val listOfWords = englishWords.filter {it.user == user && it.language == language && it.translateLanguage == translateLanguage}
        return listOfWords.random()
    }

    override fun addEnglishWord(draft: EnglishWordDraft): EnglishWord {
        val word = EnglishWord(
            id = "${draft.word}-word",
            user = draft.user,
            translateId = "${draft.word}-translated",
            word = draft.word,
            language = draft.language,
            translateLanguage = draft.translateLanguage,
            translate = draft.translate
        )
        englishWords.add(word)
        return word
    }

    override fun checkEnglishWord(word: String): EnglishWord? {
        return englishWords.firstOrNull {it.word == word}
    }
}