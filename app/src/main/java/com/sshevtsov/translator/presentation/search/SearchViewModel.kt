package com.sshevtsov.translator.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sshevtsov.translator.domain.model.DataModel
import com.sshevtsov.translator.domain.repositories.Repository
import com.sshevtsov.translator.util.SchedulersProvider
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val repository: Repository<DataModel>,
    private val schedulersProvider: SchedulersProvider
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _viewState = MutableLiveData<SearchViewState>(SearchViewState.CallToAction)

    val viewState: LiveData<SearchViewState> get() = _viewState

    fun getData(word: String, isOnline: Boolean) {
        compositeDisposable.add(
            repository.getData(word, isOnline)
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.main())
                .doOnSubscribe { doOnSubscribe().invoke(it) }
                .subscribeWith(getObserver())

        )
    }

    private fun doOnSubscribe(): (Disposable) -> Unit =
        { _viewState.value = SearchViewState.Loading }

    private fun getObserver(): DisposableSingleObserver<List<DataModel>> =
        object : DisposableSingleObserver<List<DataModel>>() {
            override fun onSuccess(data: List<DataModel>) {
                if (data.isEmpty()) {
                    _viewState.value = SearchViewState.EmptyResult
                } else {
                    _viewState.value = SearchViewState.Success(data)
                }
            }

            override fun onError(e: Throwable) {
                _viewState.value = SearchViewState.Error(e)
            }

        }

    override fun onCleared() {
        compositeDisposable.dispose()
    }
}