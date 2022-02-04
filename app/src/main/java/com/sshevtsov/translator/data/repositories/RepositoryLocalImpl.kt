package com.sshevtsov.translator.data.repositories

import com.sshevtsov.translator.data.mappers.HistoryMapper
import com.sshevtsov.translator.data.room.HistoryDao
import com.sshevtsov.translator.domain.model.history.History
import com.sshevtsov.translator.domain.repositories.RepositoryLocal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RepositoryLocalImpl(
    private val historyDao: HistoryDao,
    private val historyMapper: HistoryMapper
) : RepositoryLocal<History> {

    override suspend fun getData(): Flow<List<History>> = flow {
        historyDao.all()
            .map { historyMapper.toDomain(it) }
    }

    override suspend fun saveToDatabase(data: History) =
        historyDao.insert(historyMapper.toData(data))

}