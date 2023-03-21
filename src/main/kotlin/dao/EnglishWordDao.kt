package repository

import dto.EnglishWord
import dto.EnglishWordDraft

interface EnglishWordDao {
    fun getAllEnglishWords(user: String): List<EnglishWord>

    fun getEnglishWord(id: String, user: String, language: String): EnglishWord?

    fun addEnglishWord(draft: EnglishWordDraft): EnglishWord

    fun checkEnglishWord(word: String): EnglishWord?

}