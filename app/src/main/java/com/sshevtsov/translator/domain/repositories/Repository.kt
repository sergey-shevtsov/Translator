package com.sshevtsov.translator.domain.repositories

import io.reactivex.rxjava3.core.Single

interface Repository<T : Any> {

    fun getData(word: String, fromRemoteSource: Boolean): Single<List<T>>

}