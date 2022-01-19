package com.sshevtsov.translator.data.repositories

import com.sshevtsov.translator.data.api.TranslatorApi
import com.sshevtsov.translator.data.mappers.DataModelMapper
import com.sshevtsov.translator.domain.model.DataModel
import com.sshevtsov.translator.domain.repositories.Repository
import io.reactivex.rxjava3.core.Observable

class RepositoryImplementation(
    private val translatorApi: TranslatorApi,
    private val dataModelMapper: DataModelMapper
) : Repository<DataModel> {

    override fun getData(word: String, fromRemoteSource: Boolean): Observable<List<DataModel>> {
        return if (fromRemoteSource) {
            translatorApi.search(word)
                .flatMap { Observable.fromIterable(it) }
                .filter { !it.meanings.isNullOrEmpty() }
                .toList()
                .map { dataModelMapper.toDomain(it) }
                .flatMapObservable { Observable.just(it) }
        } else {
            TODO("Room will be here")
        }
    }

}