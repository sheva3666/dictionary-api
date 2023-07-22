package dao

import com.example.dslContext
import dto.UserScore
import exception.CreateUserException
import exception.GetUserException
import exception.UpdateUserException
import jooq.generated.tables.records.TUserScoreRecord
import jooq.generated.tables.references.T_USER_SCORE
import org.jooq.impl.DSL
import java.util.*


class UserScoreDaoImpl: UserScoreDao {
    override fun get(tenantId: UUID, userEmail: String): UserScore? {
        try {
            with(T_USER_SCORE) {
                val auth =
                    dslContext.select(DSL.asterisk()).from(T_USER_SCORE).where(C_TENANT_ID.equal(tenantId))
                        .and(C_USER_EMAIL.equal(userEmail)).fetchOneInto(
                            T_USER_SCORE
                        ) ?: return null

                return convertToUserScore(auth)
            }

        } catch (e: Exception) {
            throw GetUserException("Unable to get User by Email. Exception: $e")
        }
    }

    override fun add(userScoreRecord: TUserScoreRecord): UserScore {
        try {
            val newRecord = dslContext.newRecord(T_USER_SCORE)
            with(newRecord) {
                cUserEmail = userScoreRecord.cUserEmail
                cTenantId = userScoreRecord.cTenantId
                cScore = userScoreRecord.cScore
                store()
            }
            return convertToUserScore(newRecord)
        } catch (e: Exception) {
            throw CreateUserException("Unable to create user. Exception: $e")
        }
    }

    override fun update(userScoreRecord: TUserScoreRecord): UserScore {
        try {
            with(T_USER_SCORE) {
                val record = dslContext.select(asterisk())
                    .from(T_USER_SCORE)
                    .where(C_TENANT_ID.equal(userScoreRecord.cTenantId))
                    .and(C_USER_EMAIL.equal(userScoreRecord.cUserEmail))
                    .fetchOneInto(T_USER_SCORE)!!

                with(record) {
                    cUserEmail = userScoreRecord.cUserEmail
                    cTenantId = userScoreRecord.cTenantId
                    cScore = userScoreRecord.cScore
                    update()
                }
                return convertToUserScore(record)
            }
        } catch (e: Exception) {
            throw UpdateUserException("Unable to update User. Exception: $e")
        }
    }

    private fun convertToUserScore(record: TUserScoreRecord): UserScore {
        with(record) {
            return UserScore(
                userEmail = cUserEmail!!,
                score = cScore!!,

            )
        }
    }
}