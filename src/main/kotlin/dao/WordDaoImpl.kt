package dao

import dto.Word
import dto.WordDraft

class WordDaoImpl: WordDao {
    private val words = mutableListOf<Word>(
        Word("mother-id", "mother-translate", "admin", "mother", "мама", "ukrainian", "english"),
        Word("father-id", "father-translate", "admin", "father", "тато", "ukrainian", "english" ),

    )
    override fun getAllWords(user: String, language: String, translateLanguage: String): List<Word> {
        return words.filter {it.user == user && it.language == language}
    }

    override fun getWord( user: String, language: String, translateLanguage: String): Word {
        val listOfWords = words.filter {it.user == user && it.language == language && it.translateLanguage == translateLanguage}
        return listOfWords.random()
    }

    override fun addWord(draft: WordDraft): Word {
        val word = Word(
            id = "${draft.word}-word",
            user = draft.user,
            translateId = "${draft.word}-translated",
            word = draft.word,
            language = draft.language,
            translateLanguage = draft.translateLanguage,
            translate = draft.translate
        )
        words.add(word)
        return word
    }

    override fun checkWord(word: String): Word? {
        return words.firstOrNull {it.word == word}
    }
}