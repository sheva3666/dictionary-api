package dao

import dto.EnglishWord
import dto.EnglishWordDraft

interface EnglishWordDao {
    fun getAllEnglishWords(user: String, language: String, translateLanguage: String): List<EnglishWord>

    fun getEnglishWord(user: String, language: String, translateLanguage: String): EnglishWord

    fun addEnglishWord(draft: EnglishWordDraft): EnglishWord

    fun checkEnglishWord(word: String): EnglishWord?

}