package dao

import dto.Word
import jooq.generated.tables.records.TWordsRecord
import java.util.*

interface WordDao {
    fun getAll(tenantId: UUID, user: String, language: String, translateLanguage: String, page: Int?): List<Word>

    fun getRandomWord(tenantId: UUID, user: String, language: String, translateLanguage: String): List<Word>

    fun getLengthOfWords(tenantId: UUID, user: String, language: String, translateLanguage: String): Int

    fun getAllWithSearch(tenantId: UUID, user: String, language: String, translateLanguage: String, searchInput: String, page: Int?): List<Word>

    fun create(newWord: TWordsRecord): Word

    fun check(tenantId: UUID, word: String): Word?
}