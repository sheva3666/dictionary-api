package dao

import dto.User
import jooq.generated.tables.records.TUserRecord
import java.util.*

interface UserDao {
    fun create(newUser: TUserRecord): User

    fun getByEmail(tenantId: UUID, email: String): User?

    fun update(updatedUser: TUserRecord): User

    fun login(tenantId: UUID, email: String, password: String): User?
}