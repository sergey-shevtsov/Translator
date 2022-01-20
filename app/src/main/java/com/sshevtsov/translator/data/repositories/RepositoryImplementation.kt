package com.sshevtsov.translator.data.repositories

import com.sshevtsov.translator.data.api.TranslatorApi
import com.sshevtsov.translator.data.mappers.DataModelMapper
import com.sshevtsov.translator.domain.model.DataModel
import com.sshevtsov.translator.domain.repositories.Repository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

class RepositoryImplementation(
    private val translatorApi: TranslatorApi,
    private val dataModelMapper: DataModelMapper
) : Repository<DataModel> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): Flow<List<DataModel>> {
        return if (fromRemoteSource) {
            flowOf(
                translatorApi.search(word)
                    .asFlow()
                    .filter { !it.meanings.isNullOrEmpty() }
                    .map { dataModelMapper.toDomain(it) }
                    .toList()
            )
        } else {
            TODO("Room will be here")
        }
    }

}