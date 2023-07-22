package dto

import kotlinx.serialization.Serializable


@Serializable
data class UserScore(
    val userEmail: String,
    val score: Int,
)
