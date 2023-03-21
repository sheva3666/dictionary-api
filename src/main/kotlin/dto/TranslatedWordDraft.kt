import kotlinx.serialization.Serializable


@Serializable
data class TranslatedWordDraft(
    val user: String,
    val englishVersion: String,
    val word: String,
    val language: String,
)