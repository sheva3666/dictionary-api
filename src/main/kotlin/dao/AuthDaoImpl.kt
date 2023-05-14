package dao

import com.example.dslContext
import dto.Auth
import exception.CreateUserException
import exception.GetUserException
import exception.UpdateUserException
import jooq.generated.tables.records.TAuthRecord
import jooq.generated.tables.references.T_AUTH
import org.jooq.impl.DSL
import java.util.*


class AuthDaoImpl: AuthDao {
    override fun get(tenantId: UUID, userEmail: String): Auth? {
        try {
            with(T_AUTH) {
                val auth =
                    dslContext.select(DSL.asterisk()).from(T_AUTH).where(C_TENANT_ID.equal(tenantId))
                        .and(C_USER_EMAIL.equal(userEmail)).fetchOneInto(
                            T_AUTH
                        ) ?: return null

                return convertToAuth(auth)
            }

        } catch (e: Exception) {
            throw GetUserException("Unable to get User by Email. Exception: $e")
        }
    }

    override fun add(authRecord: TAuthRecord): Auth {
        try {
            val newRecord = dslContext.newRecord(T_AUTH)
            with(newRecord) {
                cUserEmail = authRecord.cUserEmail
                cTenantId = authRecord.cTenantId
                cUserAuth = authRecord.cUserAuth
                cLanguage = authRecord.cLanguage
                cLanguageForLearn = authRecord.cLanguageForLearn
                store()
            }
            return convertToAuth(newRecord)
        } catch (e: Exception) {
            throw CreateUserException("Unable to create user. Exception: $e")
        }
    }

    override fun update(authRecord: TAuthRecord): Auth {
        try {
            with(T_AUTH) {
                val record = dslContext.select(asterisk())
                    .from(T_AUTH)
                    .where(C_TENANT_ID.equal(authRecord.cTenantId))
                    .and(C_USER_EMAIL.equal(authRecord.cUserEmail))
                    .fetchOneInto(T_AUTH)!!

                with(record) {
                    cUserEmail = authRecord.cUserEmail
                    cUserAuth = authRecord.cUserAuth
                    cLanguage = authRecord.cLanguage
                    cLanguageForLearn = authRecord.cLanguageForLearn
                    update()
                }
                return convertToAuth(record)
            }
        } catch (e: Exception) {
            throw UpdateUserException("Unable to update User. Exception: $e")
        }
    }

    private fun convertToAuth(record: TAuthRecord): Auth {
        with(record) {
            return Auth(
                userEmail = cUserEmail!!,
                userAuth = cUserAuth!!,
                language = cLanguage!!,
                languageForLearn = cLanguageForLearn!!

            )
        }
    }
}