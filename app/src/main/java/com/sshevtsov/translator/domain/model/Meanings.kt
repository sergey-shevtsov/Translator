package com.sshevtsov.translator.domain.model

data class Meanings(
    val translation: Translation,
    val imageUrl: String,
    val soundUrl: String
) {
    companion object {
        fun of(
            translation: Translation,
            imageUrl: String?,
            soundUrl: String?
        ): Meanings =
            Meanings(translation, imageUrl.orEmpty(), soundUrl.orEmpty())
    }
}