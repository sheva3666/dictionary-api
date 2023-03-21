package dto

import kotlinx.serialization.Serializable


@Serializable
data class EnglishWord(
    val id: String,
    val translateId: String,
    val user: String,
    val word: String,
)
