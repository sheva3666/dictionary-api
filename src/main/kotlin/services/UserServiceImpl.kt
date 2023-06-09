package services

import dao.AuthDaoImpl
import dao.UserDaoImpl
import dto.User
import dto.UserDraft
import exception.UserWithGivenEmailAlreadyExistsException
import jooq.generated.tables.records.TAuthRecord
import jooq.generated.tables.records.TUserRecord
import java.util.*

class UserServiceImpl: UserService {
    private val userDao = UserDaoImpl()
    private val authDao = AuthDaoImpl()


    override fun createUser(tenantId: UUID, user: UserDraft): String {
        if (userDao.getByEmail(tenantId, user.email) != null) {
            throw UserWithGivenEmailAlreadyExistsException("User with given email: ${user.email} already exists.")
        }

        val userRecord = TUserRecord(
            cTenantId = tenantId,
            cUserEmail = user.email,
            cUserPassword = user.password,
            cUserLanguage = user.language,
            cUserLanguageForLearn = user.languageForLearn
        )

        userDao.create(userRecord)

        return "User with email: ${userRecord.cUserEmail} successfully created"


    }

    override fun updateUser(tenantId: UUID, updatedUser: UserDraft): User {

        if (userDao.login(tenantId, updatedUser.email, updatedUser.password) == null) {
            throw UserWithGivenEmailAlreadyExistsException("Password is invalid.")
        }

        val userRecord = TUserRecord(
            cTenantId = tenantId,
            cUserEmail = updatedUser.email,
            cUserPassword = updatedUser.password,
            cUserLanguage = updatedUser.language,
            cUserLanguageForLearn = updatedUser.languageForLearn

        )

        val authRecord = TAuthRecord(
            cTenantId = tenantId,
            cUserEmail = updatedUser.email,
            cUserAuth = true,
            cLanguage = updatedUser.language,
            cLanguageForLearn = updatedUser.languageForLearn
        )

        authDao.update(authRecord)

        return userDao.update(userRecord)
    }
}
