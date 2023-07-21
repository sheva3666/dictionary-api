package dto

import kotlinx.serialization.Serializable


@Serializable
data class WordsDataResponse(
    val words: List<Word>,
    val numberOfPages: Int,
    val currentPage: Int?
)
