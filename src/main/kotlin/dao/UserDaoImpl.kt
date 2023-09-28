package dao

import com.example.dslContext
import dto.User
import dto.UserVisibleData
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

    override fun getByEmail(tenantId: UUID, email: String): UserVisibleData? {
        try {
            with(T_USER) {
                val user =
                    dslContext.select(C_ID, C_USER_EMAIL, C_USER_LANGUAGE, C_USER_LANGUAGE_FOR_LEARN).from(T_USER).where(C_TENANT_ID.equal(tenantId))
                        .and(C_USER_EMAIL.equal(email)).fetchOneInto(
                            T_USER
                        ) ?: return null

                return convertToUserVisibleData(user)
            }

        } catch (e: Exception) {
            throw GetUserException("Unable to get User by Email. Exception: $e")
        }
    }

    override fun update(updatedUser: TUserRecord): UserVisibleData {
        try {
            with(T_USER) {
                val record = dslContext.select(asterisk())
                    .from(T_USER)
                    .where(C_TENANT_ID.equal(updatedUser.cTenantId))
                    .and(C_USER_EMAIL.equal(updatedUser.cUserEmail))
                    .fetchOneInto(T_USER)!!

                with(record) {
                    cUserEmail = updatedUser.cUserEmail
                    cUserLanguage = updatedUser.cUserLanguage
                    cUserLanguageForLearn = updatedUser.cUserLanguageForLearn
                    update()
                }
                return convertToUserVisibleData(record)
            }
        } catch (e: Exception) {
            throw UpdateUserException("Unable to update User. Exception: $e")
        }
    }

    override fun updatePassword(updatedUser: TUserRecord): Boolean {
        try {
            with(T_USER) {
                val record = dslContext.select(asterisk())
                    .from(T_USER)
                    .where(C_TENANT_ID.equal(updatedUser.cTenantId))
                    .and(C_USER_EMAIL.equal(updatedUser.cUserEmail))
                    .fetchOneInto(T_USER)!!

                with(record) {
                    cUserEmail = updatedUser.cUserEmail
                    cUserPassword = updatedUser.cUserPassword
                    update()
                }
                return true
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
                email = cUserEmail!!,
                password = cUserPassword!!,
                language = cUserLanguage!!,
                languageForLearn = cUserLanguageForLearn!!
            )
        }
    }

    private fun convertToUserVisibleData(record: TUserRecord): UserVisibleData {
        with(record) {
            return UserVisibleData(
                email = cUserEmail!!,
                language = cUserLanguage!!,
                languageForLearn = cUserLanguageForLearn!!
            )
        }
    }
}

