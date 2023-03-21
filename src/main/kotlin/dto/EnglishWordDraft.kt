package dto

import kotlinx.serialization.Serializable


@Serializable
data class EnglishWordDraft(
    val translateId: String,
    val user: String,
    val word: String,
)
