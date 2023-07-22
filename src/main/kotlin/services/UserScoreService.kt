package services

import dto.UserScore
import java.util.*

interface UserScoreService {

    fun getUserScore(tenantId: UUID, userEmail: String): UserScore

    fun updateUserScore(tenantId: UUID, updatedUserScore: UserScore): UserScore

    fun addUserScore(tenantId: UUID, userScore: UserScore): UserScore
}