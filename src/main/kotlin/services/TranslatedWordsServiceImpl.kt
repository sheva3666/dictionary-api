package services

import dao.TranslatedWordDaoImpl
import dto.TranslatedWord
import java.util.*

class TranslatedWordsServiceImpl: TranslatedWordsService {
    private val translatedWordDao = TranslatedWordDaoImpl()
    override fun getRandomTranslatedWords(tenantId: UUID, language: String): List<TranslatedWord> {
        val listOfWords = translatedWordDao.getRandomTranslatedWords(tenantId, language)
        return listOf<TranslatedWord>(
            listOfWords.random(),
            listOfWords.random()
        )
    }
}