package com.sshevtsov.translator.data.repositories

import com.sshevtsov.translator.data.api.TranslatorApi
import com.sshevtsov.translator.data.mappers.DataModelMapper
import com.sshevtsov.translator.domain.model.DataModel
import com.sshevtsov.translator.domain.repositories.Repository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class RepositoryImplementation @Inject constructor(
    private val translatorApi: TranslatorApi,
    private val dataModelMapper: DataModelMapper
) : Repository<DataModel> {

    override fun getData(word: String, fromRemoteSource: Boolean): Single<List<DataModel>> {
        return if (fromRemoteSource) {
            translatorApi.search(word)
                .flatMapObservable { Observable.fromIterable(it) }
                .filter { !it.meanings.isNullOrEmpty() }
                .toList()
                .map { dataModelMapper.toDomain(it) }
        } else {
            TODO("Room will be here")
        }
    }

}