package services

import dto.TranslatedWord
import java.util.*

interface TranslatedWordsService {
    fun getRandomTranslatedWords(tenantId: UUID, language: String): List<TranslatedWord>
}