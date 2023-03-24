package dao

import dto.EnglishWordDraft
import dto.TranslatedWord

class TranslatedWordDaoImpl: TranslatedWordDao {
    private val translatedWords = mutableListOf<TranslatedWord>(
        TranslatedWord("mother-translated", "admin", "мама", "ukrainian",)
    )
    override fun getTranslatedWord(id: String, user: String): TranslatedWord? {
        return translatedWords.firstOrNull {it.id == id && it.user == user}
    }

    override fun getAllTranslatedWords(user: String, language: String): List<TranslatedWord> {
        return translatedWords.filter {it.user == user && it.language == language}
    }

    override fun addTranslatedWord(draft: EnglishWordDraft): TranslatedWord {
        val translatedWord = TranslatedWord(
            id = "${draft.word}-translated",
            user = draft.user,
            word = draft.translate,
            language = draft.language,

        )
        translatedWords.add(translatedWord)
        return translatedWord
    }
}