package dao

import dto.NativeWordDraft
import dto.TranslatedWord

class TranslatedWordDaoImpl: TranslatedWordDao {
    private val translatedWords = mutableListOf<TranslatedWord>(
        TranslatedWord("mother-english-translated", "admin", "мама", "ukrainian",),
        TranslatedWord("father-english-translated", "admin", "тато", "ukrainian",),
        TranslatedWord("water-english-translated", "admin", "вода", "ukrainian",),
        TranslatedWord("fire-english-translated", "admin", "вогонь", "ukrainian",),
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

    override fun addTranslatedWord(draft: NativeWordDraft): TranslatedWord {
        val translatedWord = TranslatedWord(
            id = "${draft.word}-${draft.language}-translated",
            user = draft.user,
            word = draft.translate,
            language = draft.translateLanguage,

        )
        translatedWords.add(translatedWord)
        return translatedWord
    }
}