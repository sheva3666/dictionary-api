package dto

import kotlinx.serialization.Serializable


@Serializable
data class Auth(
    val user: String,
)
