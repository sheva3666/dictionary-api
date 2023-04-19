package dto

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class User(
    @Serializable(with = serializer.UUIDSerializer::class)
    val id: UUID,
    val name: String,
    val email: String,
    val password: String,
    var language: String,
    var languageForLearn: String
)
