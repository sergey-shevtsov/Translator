package com.sshevtsov.translator.data.repositories

import com.sshevtsov.translator.data.mappers.HistoryMapper
import com.sshevtsov.translator.data.room.HistoryDao
import com.sshevtsov.translator.domain.model.history.History
import com.sshevtsov.translator.domain.repositories.RepositoryLocal
import com.sshevtsov.translator.util.DispatcherProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext

class RepositoryLocalImpl(
    private val historyDao: HistoryDao,
    private val historyMapper: HistoryMapper,
    private val dispatcherProvider: DispatcherProvider
) : RepositoryLocal<History> {

    override suspend fun getData(): Flow<List<History>> = flowOf(
        historyDao.all().map { historyMapper.toDomain(it) }
    )

    override suspend fun saveToDatabase(data: History) = withContext(dispatcherProvider.io()) {
        historyDao.insert(historyMapper.toData(data))
    }

    override suspend fun clear() = withContext(dispatcherProvider.io()) {
        historyDao.clear()
    }

}
