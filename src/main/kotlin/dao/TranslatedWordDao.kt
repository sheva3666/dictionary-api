package dao

import dto.WordDraft
import dto.TranslatedWord

interface TranslatedWordDao {
    fun getTranslatedWord(id: String, user: String): TranslatedWord?

    fun getRandomTranslatedWords(language: String): List<TranslatedWord>

    fun getAllTranslatedWords(user: String, language: String):  List<TranslatedWord>?

    fun addTranslatedWord(draft: WordDraft): TranslatedWord
}