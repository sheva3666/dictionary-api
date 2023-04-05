package dao

import dto.EnglishWordDraft
import dto.TranslatedWord

class TranslatedWordDaoImpl: TranslatedWordDao {
    private val translatedWords = mutableListOf<TranslatedWord>(
        TranslatedWord("mother-translated", "admin", "мама", "ukrainian",),
        TranslatedWord("father-translated", "admin", "тато", "ukrainian",),
        TranslatedWord("water-translated", "admin", "вода", "ukrainian",),
        TranslatedWord("fire-translated", "admin", "вогонь", "ukrainian",),
    )
    override fun getTranslatedWord(id: String, user: String): TranslatedWord? {
        return translatedWords.firstOrNull {it.id == id && it.user == user}
    }

    override fun getRandomTranslatedWords(language: String): List<TranslatedWord> {
        return listOf<TranslatedWord>(
            translatedWords.random(),
            translatedWords.random(),
        )
    }

    override fun getAllTranslatedWords(user: String, language: String): List<TranslatedWord> {
        return translatedWords.filter {it.user == user && it.language == language}
    }

    override fun addTranslatedWord(draft: EnglishWordDraft): TranslatedWord {
        val translatedWord = TranslatedWord(
            id = "${draft.word}-translated",
            user = draft.user,
            word = draft.translate,
            language = draft.translateLanguage,

        )
        translatedWords.add(translatedWord)
        return translatedWord
    }
}