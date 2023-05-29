package dao

import com.example.dslContext
import dto.Word
import exception.CreateUserException
import exception.GetUserException
import exception.GetUserListException
import jooq.generated.tables.records.TWordsRecord
import jooq.generated.tables.references.T_WORDS
import org.jooq.impl.DSL
import org.jooq.impl.DSL.asterisk
import java.util.*

class WordDaoImpl: WordDao {
//    override fun searchWords(wordsForCurrentUser: List<Word>, searchValue: String): ArrayList<Word> {
//        var returnList = ArrayList<Word>()
//        wordsForCurrentUser.filter {it.word.contains(searchValue) || it.translate.contains(searchValue)}.map {
//            returnList.add(it)}
//        return returnList
//    }
    override fun getAll(tenantId: UUID, user: String, language: String, translateLanguage: String): List<Word> {
        try {
            return dslContext.select(asterisk()).from(T_WORDS).where(T_WORDS.C_TENANT_ID.equal(tenantId))
                .and(T_WORDS.C_USER.equal(user))
                .and(T_WORDS.C_LANGUAGE.equal(language))
                .and(T_WORDS.C_TRANSLATE_LANGUAGE.equal(translateLanguage))
                .fetchInto(T_WORDS)
                .toList()
                .map { convertToWord(it) }
        } catch (e: Exception) {
            throw GetUserListException("Unable to get List of words. Exception: $e")
        }
    }

    override fun create(newWord: TWordsRecord): Word {
        try {
            val newRecord = dslContext.newRecord(T_WORDS)
            with(newRecord) {
                cUser = newWord.cUser
                cWord = newWord.cWord
                cTranslate = newWord.cTranslate
                cTranslateLanguage = newWord.cTranslateLanguage
                cLanguage = newWord.cLanguage
                cTenantId = newWord.cTenantId
                store()
            }

            return convertToWord(newRecord)
        } catch (e: Exception) {
            throw CreateUserException("Unable to create word. Exception: $e")
        }
    }

    override fun check(tenantId: UUID, word: String): Word? {
        try {
            with(T_WORDS) {
                val word =
                    dslContext.select(DSL.asterisk()).from(T_WORDS).where(C_TENANT_ID.equal(tenantId))
                        .and(C_WORD.equal(word)).fetchOneInto(
                            T_WORDS
                        ) ?: return null

                return convertToWord(word)
            }

        } catch (e: Exception) {
            throw GetUserException("Unable to get word from list. Exception: $e")
        }
    }

    private fun convertToWord(record: TWordsRecord): Word {
        with(record) {
            return Word(
                id = cId!!,
                user = cUser!!,
                word = cWord!!,
                translate = cTranslate!!,
                translateLanguage = cTranslateLanguage!!,
                language = cLanguage!!,
            )
        }
    }
}