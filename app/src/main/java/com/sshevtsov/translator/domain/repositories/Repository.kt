package com.sshevtsov.translator.domain.repositories

import io.reactivex.rxjava3.core.Observable

interface Repository<T : Any> {

    fun getData(word: String, fromRemoteSource: Boolean): Observable<List<T>>

}