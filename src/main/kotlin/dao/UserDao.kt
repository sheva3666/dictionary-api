package dao

import dto.User
import dto.UserDraft

interface UserDao {
    fun getUser(id: String, password: String): User?

    fun checkUser(id: String): User?

    fun addUser(draft: UserDraft): User

    fun updateUser(id: String, draft: UserDraft): Boolean
}