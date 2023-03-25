package dao

import dto.Auth

class AuthDaoImpl: AuthDao {
    private val auths = mutableListOf<Auth>(
        Auth("admin")
    )
    override fun getAuth(id: String): Auth? {
        return auths.firstOrNull {it.user == id}
    }

    override fun addAuth(email: String): Auth {
        val auth = Auth(
            user = email
        )
        auths.add(auth)
        return auth
    }

    override fun removeAuth(user: String): Boolean {
        return auths.removeIf {it.user == user}
    }
}