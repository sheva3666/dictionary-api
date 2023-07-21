package services

import dao.TranslatedWordDaoImpl
import dto.TranslatedWord
import java.util.*

class TranslatedWordsServiceImpl: TranslatedWordsService {
    private val translatedWordDao = TranslatedWordDaoImpl()

    override fun getRandomTranslatedWords(tenantId: UUID, language: String): List<TranslatedWord> {
        return translatedWordDao.getRandomTranslatedWords(tenantId, language)
    }
}