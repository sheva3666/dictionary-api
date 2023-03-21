package dao

import TranslatedWordDraft
import dto.TranslatedWord

interface TranslatedWordDao {
    fun getTranslatedWord(id: String, user: String): TranslatedWord?

    fun getAllTranslatedWords(user: String, language: String):  List<TranslatedWord>?

    fun addTranslatedWord(draft: TranslatedWordDraft): TranslatedWord
}