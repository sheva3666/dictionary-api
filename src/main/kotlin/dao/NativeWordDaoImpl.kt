package dao

import dto.NativeWord
import dto.NativeWordDraft

class NativeWordDaoImpl: NativeWordDao {
    private val englishWords = mutableListOf<NativeWord>(
        NativeWord("mother-id", "mother-translate", "admin", "mother", "мама", "ukrainian", "english"),
        NativeWord("father-id", "father-translate", "admin", "father", "тато", "ukrainian", "english" ),

    )
    override fun getAllNativeWords(user: String, language: String, translateLanguage: String): List<NativeWord> {
        return englishWords.filter {it.user == user && it.language == language}
    }

    override fun getNativeWord( user: String, language: String, translateLanguage: String): NativeWord {
        val listOfWords = englishWords.filter {it.user == user && it.language == language && it.translateLanguage == translateLanguage}
        return listOfWords.random()
    }

    override fun addNativeWord(draft: NativeWordDraft): NativeWord {
        val word = NativeWord(
            id = "${draft.word}-word",
            user = draft.user,
            translateId = "${draft.word}-translated",
            word = draft.word,
            language = draft.language,
            translateLanguage = draft.translateLanguage,
            translate = draft.translate
        )
        englishWords.add(word)
        return word
    }

    override fun checkNativeWord(word: String): NativeWord? {
        return englishWords.firstOrNull {it.word == word}
    }
}