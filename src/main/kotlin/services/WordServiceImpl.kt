package services

import dao.TranslatedWordDaoImpl
import dao.WordDaoImpl
import dto.Word
import dto.WordDraft
import dto.WordsDataResponse
import exception.UserWithGivenEmailAlreadyExistsException
import jooq.generated.tables.records.TTranslatedWordsRecord
import jooq.generated.tables.records.TWordsRecord
import java.util.*

class WordServiceImpl: WordService {
    private val wordDao = WordDaoImpl()
    private val translatedWordDao = TranslatedWordDaoImpl()

    override fun getAllWordsWithSearch(tenantId: UUID, user: String, language: String, translateLanguage: String, searchInput: String, page: Int?): WordsDataResponse {
        val currentPage = if (page == 1) {
            0
        } else {
            page?.minus(1)?.times(10)
        }

    val words = wordDao.getAllWithSearch(tenantId, user, language, translateLanguage, searchInput, currentPage)
        return WordsDataResponse(
            words,
            words.size / 10 + 1,
            page
        )

    }

    override fun getAllWords(tenantId: UUID, user: String, language: String, translateLanguage: String, page: Int?): WordsDataResponse {
        val currentPage = if (page == 1) {
             1
        } else {
            page?.minus(1)?.times(10)
        }

        val length = wordDao.getLengthOfWords(tenantId, user, language, translateLanguage) / 10
        return WordsDataResponse(
            wordDao.getAll(tenantId, user, language, translateLanguage, currentPage),
            length + 1,
            page
        )
    }

    override fun getWord(tenantId: UUID, user: String, language: String, translateLanguage: String): Word {
        val wordsFotUser = wordDao.getRandomWord (tenantId, user, language, translateLanguage)
        return wordsFotUser[0]
    }

    override fun addWord(tenantId: UUID, newWord: WordDraft): Word {
        if (wordDao.check(tenantId, newWord.word, newWord.translate) != null) {
            throw UserWithGivenEmailAlreadyExistsException("Word: ${newWord.word} with translation ${newWord.translate} already exists.")
        }

        val translatedWord = TTranslatedWordsRecord(
            cTenantId = tenantId,
            cUser = newWord.user,
            cWord = newWord.translate,
            cLanguage = newWord.translateLanguage,

        )

        translatedWordDao.addTranslatedWord(translatedWord)



        val wordRecord = TWordsRecord(
            cTenantId = tenantId,
            cUser = newWord.user,
            cWord = newWord.word,
            cTranslate = newWord.translate,
            cLanguage = newWord.language,
            cTranslateLanguage = newWord.translateLanguage

        )

        return wordDao.create(wordRecord)
    }
}