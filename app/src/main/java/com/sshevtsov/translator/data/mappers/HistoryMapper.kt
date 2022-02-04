package com.sshevtsov.translator.data.mappers

import com.sshevtsov.translator.data.room.model.HistoryEntity
import com.sshevtsov.translator.domain.model.history.History

class HistoryMapper {

    fun toDomain(entity: HistoryEntity): History =
        History.of(
            timestamp = entity.timestamp,
            word = entity.word
        )

    fun toData(history: History): HistoryEntity =
        HistoryEntity(
            word = history.word,
            timestamp = history.timestamp
        )
}