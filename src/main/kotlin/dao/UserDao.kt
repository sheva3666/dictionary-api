package dao

import dto.User
import dto.UserVisibleData
import jooq.generated.tables.records.TUserRecord
import java.util.*

interface UserDao {
    fun create(newUser: TUserRecord): User

    fun getByEmail(tenantId: UUID, email: String): UserVisibleData?

    fun update(updatedUser: TUserRecord): UserVisibleData

    fun updatePassword(updatedUser: TUserRecord): Boolean

    fun login(tenantId: UUID, email: String, password: String): User?
}