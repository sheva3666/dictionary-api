package dao

import dto.NativeWord
import dto.NativeWordDraft

interface NativeWordDao {
    fun getAllNativeWords(user: String, language: String, translateLanguage: String): List<NativeWord>

    fun getNativeWord(user: String, language: String, translateLanguage: String): NativeWord

    fun addNativeWord(draft: NativeWordDraft): NativeWord

    fun checkNativeWord(word: String): NativeWord?

}