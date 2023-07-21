package dao

import com.example.dslContext
import dto.TranslatedWord
import exception.CreateUserException
import exception.GetUserListException
import jooq.generated.tables.records.TTranslatedWordsRecord
import jooq.generated.tables.references.T_TRANSLATED_WORDS
import org.jooq.impl.DSL
import org.jooq.impl.DSL.rand
import java.util.*

class TranslatedWordDaoImpl: TranslatedWordDao {
    override fun getRandomTranslatedWords(tenantId: UUID, language: String): List<TranslatedWord> {
        try {
            return dslContext.select(DSL.asterisk()).from(T_TRANSLATED_WORDS).where(T_TRANSLATED_WORDS.C_TENANT_ID.equal(tenantId))
                .and(T_TRANSLATED_WORDS.C_LANGUAGE.equal(language)).orderBy(rand()).limit(2)
                .fetchInto(T_TRANSLATED_WORDS)
                .toList()
                .map { convertToTranslatedWord(it) }
        } catch (e: Exception) {
            throw GetUserListException("Unable to get list of translated words. Exception: $e")
        }
    }

    override fun addTranslatedWord(newWord: TTranslatedWordsRecord): TranslatedWord {
        try {
            val newRecord = dslContext.newRecord(T_TRANSLATED_WORDS)
            with(newRecord) {
                cUser = newWord.cUser
                cWord = newWord.cWord
                cLanguage = newWord.cLanguage
                cTenantId = newWord.cTenantId
                store()
            }

            return convertToTranslatedWord(newRecord)
        } catch (e: Exception) {
            throw CreateUserException("Unable to create word. Exception: $e")
        }
    }
    }

    private fun convertToTranslatedWord(record: TTranslatedWordsRecord): TranslatedWord {
        with(record) {
            return TranslatedWord(
                id = cId!!,
                user = cUser!!,
                word = cWord!!,
                language = cLanguage!!,
            )
        }
    }
