package services

import dto.User
import dto.UserDraft
import java.util.*

interface UserService {
    fun createUser(tenantId: UUID, user: UserDraft): String

    fun updateUser(tenantId: UUID, updatedUser: User): User

}