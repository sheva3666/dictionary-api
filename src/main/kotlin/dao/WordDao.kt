package dao

import dto.Word
import dto.WordDraft

interface WordDao {
    fun getAllWords(user: String, language: String, translateLanguage: String): List<Word>

    fun getWord(user: String, language: String, translateLanguage: String): Word

    fun addWord(draft: WordDraft): Word

    fun checkWord(word: String): Word?

}