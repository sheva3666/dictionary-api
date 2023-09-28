package services

import dto.UserDraft
import dto.UserVisibleData
import java.util.*

interface UserService {
    fun createUser(tenantId: UUID, user: UserDraft): String

    fun updateUser(tenantId: UUID, updatedUser: UserVisibleData): UserVisibleData

    fun updatePassword(tenantId: UUID, email: String, password: String, newPassword: String): Boolean


    fun getUser(tenantId: UUID, email: String): UserVisibleData?

}