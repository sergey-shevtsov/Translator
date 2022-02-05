package com.sshevtsov.translator.domain.model.history

data class History(
    val timestamp: Long,
    val word: String
) {
    companion object {
        fun of(
            timestamp: Long,
            word: String
        ): History =
            History(timestamp, word)
    }
}
