package services

import dao.UserScoreDaoImpl
import dto.UserScore
import exception.UserNotFoundException
import exception.UserWithGivenEmailAlreadyExistsException
import jooq.generated.tables.records.TUserScoreRecord
import java.util.*

class UserScoreServiceImpl: UserScoreService {
    private val userScoreDao = UserScoreDaoImpl()

    override fun getUserScore(tenantId: UUID, userEmail: String): UserScore {
        return userScoreDao.get(tenantId, userEmail)
            ?: throw UserNotFoundException("Dear: $userEmail please first you should login.")
    }

    override fun updateUserScore(tenantId: UUID, updatedUserScore: UserScore): UserScore {
        val currentScore = userScoreDao.get(tenantId, updatedUserScore.userEmail)

        if (currentScore == null) {
            throw UserWithGivenEmailAlreadyExistsException("Dear: ${updatedUserScore.userEmail} please first you should start exercise.")
        }

        val newScore = if (updatedUserScore.score <= 0) {
            currentScore.score.minus(1)
        } else {
            currentScore.score.plus(1)
        }

        val userScoreRecord = TUserScoreRecord(
            cTenantId = tenantId,
            cUserEmail = updatedUserScore.userEmail,
            cScore = newScore,
        )

        return userScoreDao.update(userScoreRecord)
    }

    override fun addUserScore(tenantId: UUID, userScore: UserScore): UserScore {

        val authRecord = TUserScoreRecord(
            cUserEmail = userScore.userEmail,
            cTenantId = tenantId,
            cScore = userScore.score
        )

        if (userScoreDao.get(tenantId, userScore.userEmail) !== null) {
            return userScoreDao.update(authRecord)
        }

        return userScoreDao.add(authRecord)
    }
}