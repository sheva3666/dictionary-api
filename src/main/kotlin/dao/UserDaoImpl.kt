package dao

import com.example.dslContext
import dto.User
import exception.CreateUserException
import exception.GetUserException
import exception.UpdateUserException
import jooq.generated.tables.records.TUserRecord
import jooq.generated.tables.references.T_USER
import org.jooq.impl.DSL
import java.util.*

class UserDaoImpl: UserDao {
    override fun create(newUser: TUserRecord): User {
        try {
            val newRecord = dslContext.newRecord(T_USER)
            with(newRecord) {
                cUserEmail = newUser.cUserEmail
                cUserName = newUser.cUserName
                cUserPassword = newUser.cUserPassword
                cUserLanguage = newUser.cUserLanguage
                cUserLanguageForLearn = newUser.cUserLanguageForLearn
                cTenantId = newUser.cTenantId
                store()
            }

            return convertToUser(newRecord)
        } catch (e: Exception) {
            throw CreateUserException("Unable to create user. Exception: $e")
        }
    }

    override fun getByEmail(tenantId: UUID, email: String): User? {
        try {
            with(T_USER) {
                val user =
                    dslContext.select(DSL.asterisk()).from(T_USER).where(C_TENANT_ID.equal(tenantId))
                        .and(C_USER_EMAIL.equal(email)).fetchOneInto(
                            T_USER
                        ) ?: return null

                return convertToUser(user)
            }

        } catch (e: Exception) {
            throw GetUserException("Unable to get User by Email. Exception: $e")
        }
    }

    override fun update(updatedUser: TUserRecord): User {
        try {
            with(T_USER) {
                val record = dslContext.select(asterisk())
                    .from(T_USER)
                    .where(C_TENANT_ID.equal(updatedUser.cTenantId))
                    .and(C_ID.equal(updatedUser.cId))
                    .fetchOneInto(T_USER)!!

                with(record) {
                    cUserEmail = updatedUser.cUserEmail
                    cUserName = updatedUser.cUserName
                    cUserPassword = updatedUser.cUserPassword
                    cUserLanguage = updatedUser.cUserLanguage
                    cUserLanguageForLearn = updatedUser.cUserLanguageForLearn
                    update()
                }
                return convertToUser(record)
            }
        } catch (e: Exception) {
            throw UpdateUserException("Unable to update User. Exception: $e")
        }
    }

    override fun login(tenantId: UUID, email: String, password: String): User? {
        try {
            with(T_USER) {
                val user =
                    dslContext.select(DSL.asterisk()).from(T_USER).where(C_TENANT_ID.equal(tenantId))
                        .and(C_USER_EMAIL.equal(email))
                        .and(C_USER_PASSWORD.equal(password))
                        .fetchOneInto(
                            T_USER
                        ) ?: return null
            println(user)
                return convertToUser(user)
            }

        } catch (e: Exception) {
            throw GetUserException("Unable to get User by Email. Exception: $e")
        }
    }

    private fun convertToUser(record: TUserRecord): User {
        with(record) {
            return User(
                id = cId!!,
                name = cUserName!!,
                email = cUserEmail!!,
                password = cUserPassword!!,
                language = cUserLanguage!!,
                languageForLearn = cUserLanguageForLearn!!
            )
        }
    }
}

