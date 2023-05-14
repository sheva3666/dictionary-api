package dto

import kotlinx.serialization.Serializable


@Serializable
data class Auth(
    val userEmail: String,
    val userAuth: Boolean,
    val language: String,
    val languageForLearn: String,

)
