package com.sshevtsov.translator.domain.model

data class DataModel(
    val text: String,
    val meanings: List<Meanings>
) {
    companion object {
        fun of(
            text: String?,
            meanings: List<Meanings>
        ): DataModel =
            DataModel(text.orEmpty(), meanings)
    }
}