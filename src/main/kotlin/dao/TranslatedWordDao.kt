package dao

import dto.EnglishWordDraft
import dto.TranslatedWord

interface TranslatedWordDao {
    fun getTranslatedWord(id: String, user: String): TranslatedWord?

    fun getAllTranslatedWords(user: String, language: String):  List<TranslatedWord>?

    fun addTranslatedWord(draft: EnglishWordDraft): TranslatedWord
}