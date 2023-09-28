package services

import dto.User
import java.util.*

interface AuthService {

    fun loginUser(tenantId: UUID, email: String, password: String): User
}