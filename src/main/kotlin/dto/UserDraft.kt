package dto

import kotlinx.serialization.Serializable


@Serializable
data class UserDraft(
    val email: String,
    val password: String,
    val language: String,
    val languageForLearn: String
)
