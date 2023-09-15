package dao

import dto.TranslatedWord
import jooq.generated.tables.records.TTranslatedWordsRecord
import java.util.*

interface TranslatedWordDao {

    fun getRandomTranslatedWords(tenantId: UUID, language: String, limit: Int): List<TranslatedWord>

    fun addTranslatedWord(newWord: TTranslatedWordsRecord): TranslatedWord
}