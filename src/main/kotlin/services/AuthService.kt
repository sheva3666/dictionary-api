package services

import dto.Auth
import java.util.*

interface AuthService {

    fun getAuth(tenantId: UUID, updatedAuth: Auth): Auth

    fun updateAuth(tenantId: UUID, updatedAuth: Auth): Auth
}