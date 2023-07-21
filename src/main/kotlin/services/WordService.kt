package services

import dto.Word
import dto.WordDraft
import dto.WordsDataResponse
import java.util.*

interface WordService {
    fun getAllWords(tenantId: UUID, user: String, language: String, translateLanguage: String, page: Int?): WordsDataResponse

    fun getAllWordsWithSearch(tenantId: UUID, user: String, language: String, translateLanguage: String, searchInput: String, page: Int?): WordsDataResponse

    fun getWord(tenantId: UUID, user: String, language: String, translateLanguage: String): Word

    fun addWord(tenantId: UUID, newWord: WordDraft): Word
}