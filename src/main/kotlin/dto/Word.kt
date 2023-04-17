package dto

import kotlinx.serialization.Serializable


@Serializable
data class Word(
    val id: String,
    val translateId: String,
    val user: String,
    val word: String,
    val translate: String,
    val translateLanguage: String,
    val language: String
)