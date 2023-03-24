package dto

import kotlinx.serialization.Serializable


@Serializable
data class TranslatedWord(
    val id: String,
    val user: String,
    val word: String,
    val language: String,
)
