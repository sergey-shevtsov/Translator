package com.sshevtsov.translator.domain.model

data class Translation(
    val text: String
) {
    companion object {
        fun of(text: String?): Translation =
            Translation(text.orEmpty())
    }
}