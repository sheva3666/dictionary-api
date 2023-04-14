package dto

import kotlinx.serialization.Serializable


@Serializable
data class WordDraft(
    val user: String,
    val word: String,
    val language: String,
    val translate: String,
    val translateLanguage: String,
)
