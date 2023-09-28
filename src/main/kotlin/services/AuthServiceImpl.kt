package services

import dao.UserDaoImpl
import dto.User
import exception.UserNotFoundException
import java.util.*

class AuthServiceImpl: AuthService {
    private val userDao = UserDaoImpl()

    override fun loginUser(tenantId: UUID, email: String, password: String): User {
        return userDao.login(tenantId, email, password)
            ?: throw UserNotFoundException("User email or password is invalid.")

    }
}