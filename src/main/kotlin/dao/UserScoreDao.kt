package dao

import dto.UserScore
import jooq.generated.tables.records.TUserScoreRecord
import java.util.*

interface UserScoreDao {
    fun get(tenantId: UUID, userEmail: String): UserScore?

    fun add(userScoreRecord: TUserScoreRecord): UserScore

    fun update(userScoreRecord: TUserScoreRecord): UserScore
}