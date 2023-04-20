package services

import dao.AuthDaoImpl
import dao.UserDaoImpl
import dto.Auth
import dto.User
import dto.UserDraft
import exception.UserWithGivenEmailAlreadyExistsException
import jooq.generated.tables.records.TAuthRecord
import jooq.generated.tables.records.TUserRecord
import java.util.*

class UserServiceImpl: UserService {
    private val userDao = UserDaoImpl()
    private val authDao = AuthDaoImpl()

    override fun createUser(tenantId: UUID, user: UserDraft): User {
        if (userDao.getByEmail(tenantId, user.email) != null) {
            throw UserWithGivenEmailAlreadyExistsException("User with given email: ${user.email} already exists.")
        }

        val userRecord = TUserRecord(
            cTenantId = tenantId,
            cUserEmail = user.email,
            cUserPassword = user.password,
            cUserName = user.name,
            cUserLanguage = user.language,
            cUserLanguageForLearn = user.languageForLearn
        )

        return userDao.create(userRecord)
    }

    override fun updateUser(tenantId: UUID, updatedUser: User): User {

        if (userDao.getByEmail(tenantId, updatedUser.email) != null) {
            throw UserWithGivenEmailAlreadyExistsException("User with given email: ${updatedUser.email} already exists.")
        }

        val userRecord = TUserRecord(
            cId = updatedUser.id,
            cTenantId = tenantId,
            cUserEmail = updatedUser.email,
            cUserPassword = updatedUser.password,
            cUserName = updatedUser.name,
            cUserLanguage = updatedUser.language,
            cUserLanguageForLearn = updatedUser.languageForLearn

        )

        return userDao.update(userRecord)
    }

    override fun loginUser(tenantId: UUID, email: String, password: String): Auth {
        if (userDao.login(tenantId, email, password) == null) {
            throw UserWithGivenEmailAlreadyExistsException("User email or password is invalid.")
        }

        val authRecord = TAuthRecord(
            cUserEmail = email,
            cTenantId = tenantId,
            cUserAuth = true,

        )

        if (authDao.get(tenantId, email) !== null) {
            return authDao.update(authRecord)
        }

        return authDao.add(authRecord)
    }

}
