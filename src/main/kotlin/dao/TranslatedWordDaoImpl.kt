package dao

import TranslatedWordDraft
import dto.TranslatedWord

class TranslatedWordDaoImpl: TranslatedWordDao {
    private val translatedWords = mutableListOf<TranslatedWord>(
        TranslatedWord("mother-translated", "admin", "mother", "мама", "ukrainian",)
    )
    override fun getTranslatedWord(id: String, user: String): TranslatedWord? {
        return translatedWords.firstOrNull {it.id == id && it.user == user}
    }

    override fun getAllTranslatedWords(user: String, language: String): List<TranslatedWord>? {
        return translatedWords.filter {it.user == user && it.language == language}
    }

    override fun addTranslatedWord(draft: TranslatedWordDraft): TranslatedWord {
        val draftId = draft.word
        val translatedWord = TranslatedWord(
            id = "$draftId-translated",
            user = draft.user,
            word = draft.word,
            language = draft.language,
            englishVersion = draft.englishVersion
        )
        translatedWords.add(translatedWord)
        return translatedWord
    }
}