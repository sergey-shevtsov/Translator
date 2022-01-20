package com.sshevtsov.translator.domain.repositories

import kotlinx.coroutines.flow.Flow

interface Repository<T : Any> {

    suspend fun getData(word: String, fromRemoteSource: Boolean): Flow<List<T>>

}