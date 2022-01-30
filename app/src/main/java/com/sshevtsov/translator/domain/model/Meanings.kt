package com.sshevtsov.translator.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Meanings(
    val translation: Translation,
    val imageUrl: String,
    val soundUrl: String
) : Parcelable {
    companion object {
        fun of(
            translation: Translation,
            imageUrl: String?,
            soundUrl: String?
        ): Meanings =
            Meanings(translation, imageUrl.orEmpty(), soundUrl.orEmpty())
    }
}