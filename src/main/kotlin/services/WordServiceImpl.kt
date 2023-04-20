package services

import dao.WordDaoImpl
import dto.Word
import dto.WordDraft
import exception.UserWithGivenEmailAlreadyExistsException
import jooq.generated.tables.records.TWordsRecord
import java.util.*

class WordServiceImpl: WordService {
    private val wordDao = WordDaoImpl()
    override fun getAllWords(tenantId: UUID, user: String, language: String, translateLanguage: String): List<Word> {
        return wordDao.getAll(tenantId, user, language, translateLanguage)
    }

    override fun getWord(tenantId: UUID, user: String, language: String, translateLanguage: String): Word {
        TODO("Not yet implemented")
    }

    override fun addWord(tenantId: UUID, newWord: WordDraft): Word {
        if (wordDao.check(tenantId, newWord.word) != null) {
            throw UserWithGivenEmailAlreadyExistsException("Word: ${newWord.word} already exists.")
        }

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

    override fun checkWord(tenantId: UUID, word: String): Word? {
        TODO("Not yet implemented")
    }

    override fun searchWords(tenantId: UUID, wordsForCurrentUser: List<Word>, searchValue: String): ArrayList<Word> {
        TODO("Not yet implemented")
    }
}