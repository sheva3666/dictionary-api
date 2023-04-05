package dao

import dto.User
import dto.UserDraft

class UserDaoImpl: UserDao {
    private val users = mutableListOf<User>(
        User("admin", "admin", "admin", "admin", "ukrainian", "english")
    )
    override fun getUser(id: String, password: String): User? {
        return users.firstOrNull {it.id == id && it.password == password}
    }

    override fun checkUser(id: String): User? {
        return users.firstOrNull {it.id == id}
    }

    override fun addUser(draft: UserDraft): User {
        val user = User(
            id = draft.email,
            name = draft.name,
            email = draft.email,
            password = draft.password,
            language = draft.language,
            languageForLearn = draft.languageForLearn
        )
        users.add(user)
        return user
    }

    override fun updateUser(id: String, draft: UserDraft): Boolean {
        val user = users.firstOrNull {it.id == id}
            ?: return false
        user.language = draft.language
        user.languageForLearn = draft.languageForLearn
        return true
    }
}

