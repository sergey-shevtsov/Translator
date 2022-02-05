package com.sshevtsov.translator.domain.model.search

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Translation(
    val text: String
) : Parcelable {
    companion object {
        fun of(text: String?): Translation =
            Translation(text.orEmpty())
    }
}