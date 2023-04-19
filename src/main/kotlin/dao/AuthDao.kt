package dao

import dto.Auth
import jooq.generated.tables.records.TAuthRecord
import java.util.*

interface AuthDao {
    fun get(tenantId: UUID, authEmail: String): Auth?

    fun add(auth: TAuthRecord): Auth

    fun update(authRecord: TAuthRecord): Auth
}