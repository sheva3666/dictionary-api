package services

import dao.UserDaoImpl
import dto.UserDraft
import dto.UserVisibleData
import exception.UserNotFoundException
import exception.UserWithGivenEmailAlreadyExistsException
import jooq.generated.tables.records.TUserRecord
import java.util.*

class UserServiceImpl: UserService {
    private val userDao = UserDaoImpl()

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

    override fun updateUser(tenantId: UUID, updatedUser: UserVisibleData): UserVisibleData {

        val userRecord = TUserRecord(
            cTenantId = tenantId,
            cUserEmail = updatedUser.email,
            cUserLanguage = updatedUser.language,
            cUserLanguageForLearn = updatedUser.languageForLearn
        )

        return userDao.update(userRecord)
    }

    override fun updatePassword(tenantId: UUID, email: String, password: String, newPassword: String): Boolean {
        if (userDao.login(tenantId, email, password) == null) {
            throw UserNotFoundException("Incorrect password.")
        }

        val userRecord = TUserRecord(
            cTenantId = tenantId,
            cUserEmail = email,
            cUserPassword = newPassword,
        )

        return userDao.updatePassword(userRecord)

    }

    override fun getUser(tenantId: UUID, email: String): UserVisibleData? {

        if (userDao.getByEmail(tenantId, email) == null) {
            throw UserNotFoundException("User with email: $email doesn't exist.")
        }

        return userDao.getByEmail(tenantId, email)
    }
}
