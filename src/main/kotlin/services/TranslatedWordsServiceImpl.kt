package services

import dao.TranslatedWordDaoImpl
import dao.UserScoreDaoImpl
import dto.TranslatedWord
import java.util.*

class TranslatedWordsServiceImpl: TranslatedWordsService {
    private val translatedWordDao = TranslatedWordDaoImpl()
    private val userScoreDao = UserScoreDaoImpl()


    override fun getRandomTranslatedWords(tenantId: UUID, userEmail: String, languageForLearn: String, language: String): List<TranslatedWord> {
        val limit = getLimit(tenantId, userEmail, languageForLearn)
        return translatedWordDao.getRandomTranslatedWords(tenantId, language, limit)
    }

    private fun getLimit(tenantId: UUID, userEmail: String, languageForLearn: String): Int {
        val userScore = userScoreDao.get(tenantId, userEmail, languageForLearn)?.score
        return if (userScore != null) {
             when {
                userScore < 50 -> 1
                userScore in 51..150 -> 2
                userScore > 150 -> 3
                else -> 1
            }
        } else 1
    }
}