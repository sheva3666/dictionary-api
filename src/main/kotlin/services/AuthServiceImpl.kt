package services

import dao.AuthDaoImpl
import dto.Auth
import exception.UserWithGivenEmailAlreadyExistsException
import jooq.generated.tables.records.TAuthRecord
import java.util.*

class AuthServiceImpl: AuthService {
    private val authDao = AuthDaoImpl()
    override fun getAuth(tenantId: UUID, userEmail: String): Auth {
        return authDao.get(tenantId, userEmail)?:
            throw UserWithGivenEmailAlreadyExistsException("Dear: $userEmail please first you should login.")
    }

    override fun updateAuth(tenantId: UUID, updatedAuth: Auth): Auth {
        if (authDao.get(tenantId, updatedAuth.userEmail) == null) {
            throw UserWithGivenEmailAlreadyExistsException("Dear: ${updatedAuth.userEmail} please first you should login.")
        }

        val authRecord = TAuthRecord(
            cTenantId = tenantId,
            cUserEmail = updatedAuth.userEmail,
            cUserAuth = updatedAuth.userAuth,
        )

        return authDao.update(authRecord)
    }
}