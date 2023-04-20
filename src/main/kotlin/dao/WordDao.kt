package dao

import dto.Word
import jooq.generated.tables.records.TWordsRecord
import java.util.*

interface WordDao {
    fun getAll(tenantId: UUID, user: String, language: String, translateLanguage: String): List<Word>

    fun create(newWord: TWordsRecord): Word

    fun check(tenantId: UUID, word: String): Word?
}