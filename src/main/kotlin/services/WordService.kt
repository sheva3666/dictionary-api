package services

import dto.Word
import dto.WordDraft
import java.util.*

interface WordService {
    fun getAllWords(tenantId: UUID, user: String, language: String, translateLanguage: String): List<Word>

    fun getWord(tenantId: UUID, user: String, language: String, translateLanguage: String): Word

    fun addWord(tenantId: UUID, newWord: WordDraft): Word

    fun checkWord(tenantId: UUID, word: String): Word?

    fun searchWords(tenantId: UUID, user: String, language: String, translateLanguage: String, searchValue: String): ArrayList<Word>
}