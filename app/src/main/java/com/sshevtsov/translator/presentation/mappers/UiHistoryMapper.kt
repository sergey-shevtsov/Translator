package com.sshevtsov.translator.presentation.mappers

import com.sshevtsov.translator.domain.model.history.History

class UiHistoryMapper {

    fun toDomain(word: String): History =
        History(
            timestamp = System.currentTimeMillis(),
            word = word
        )
}