package dto

import kotlinx.serialization.Serializable

@Serializable
data class UserVisibleData(
    val email: String,
    var language: String,
    var languageForLearn: String
)
