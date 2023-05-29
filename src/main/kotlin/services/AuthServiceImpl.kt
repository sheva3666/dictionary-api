package services

import dao.AuthDaoImpl
import dao.UserDaoImpl
import dto.Auth
import exception.UserNotFoundException
import exception.UserWithGivenEmailAlreadyExistsException
import jooq.generated.tables.records.TAuthRecord
import java.util.*

class AuthServiceImpl: AuthService {
    private val authDao = AuthDaoImpl()
    private val userDao = UserDaoImpl()

    override fun getAuth(tenantId: UUID, userEmail: String): Auth {
        return authDao.get(tenantId, userEmail)
            ?: throw UserNotFoundException("Dear: $userEmail please first you should login.")
    }

    override fun updateAuth(tenantId: UUID, updatedAuth: Auth): Auth {
        if (authDao.get(tenantId, updatedAuth.userEmail) == null) {
            throw UserWithGivenEmailAlreadyExistsException("Dear: ${updatedAuth.userEmail} please first you should login.")
        }

        val authRecord = TAuthRecord(
            cTenantId = tenantId,
            cUserEmail = updatedAuth.userEmail,
            cUserAuth = updatedAuth.userAuth,
            cLanguage = updatedAuth.language,
            cLanguageForLearn = updatedAuth.languageForLearn
        )

        return authDao.update(authRecord)
    }

    override fun loginUser(tenantId: UUID, email: String, password: String): Auth {
        val authUser = userDao.login(tenantId, email, password)
            ?: throw UserNotFoundException("User email or password is invalid.")


        val authRecord = TAuthRecord(
            cUserEmail = email,
            cTenantId = tenantId,
            cUserAuth = true,
            cLanguage = authUser.language,
            cLanguageForLearn = authUser.languageForLearn
        )

        if (authDao.get(tenantId, email) !== null) {
            return authDao.update(authRecord)
        }

        return authDao.add(authRecord)
    }
}