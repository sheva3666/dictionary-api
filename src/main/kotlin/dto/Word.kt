package dto

import kotlinx.serialization.Serializable
import java.util.*


@Serializable
data class Word(
    @Serializable(with = serializer.UUIDSerializer::class)
    val id: UUID,
    val user: String,
    val word: String,
    val translate: String,
    val translateLanguage: String,
    val language: String
)
