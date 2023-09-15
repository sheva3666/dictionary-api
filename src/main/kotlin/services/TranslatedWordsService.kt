package services

import dto.TranslatedWord
import java.util.*

interface TranslatedWordsService {
    fun getRandomTranslatedWords(tenantId: UUID, userEmail: String, languageForLearn: String, language: String): List<TranslatedWord>
}