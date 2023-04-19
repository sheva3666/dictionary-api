package services

import dto.Auth
import dto.User
import dto.UserDraft
import java.util.*

interface UserService {
    fun createUser(tenantId: UUID, user: UserDraft): User

    fun updateUser(tenantId: UUID, updatedUser: User): User

    fun loginUser(tenantId: UUID, email: String, password: String): Auth

}