package dao

import dto.Auth

interface AuthDao {
    fun getAuth(id: String): Auth?

    fun addAuth(email: String): Auth

    fun removeAuth(user: String): Boolean
}