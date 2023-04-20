package dto

import kotlinx.serialization.Serializable
import java.util.*


@Serializable
data class TranslatedWord(
    @Serializable(with = serializer.UUIDSerializer::class)
    val id: UUID,
    val user: String,
    val word: String,
    val language: String,
)
