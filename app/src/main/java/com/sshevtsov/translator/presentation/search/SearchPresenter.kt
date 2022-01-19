package com.sshevtsov.translator.presentation.search

import com.sshevtsov.translator.domain.model.DataModel
import com.sshevtsov.translator.domain.repositories.Repository
import com.sshevtsov.translator.util.SchedulersProvider
import io.reactivex.rxjava3.disposables.CompositeDisposable

class SearchPresenter(
    private val repository: Repository<DataModel>,
    private val schedulersProvider: SchedulersProvider
) : SearchContract.Presenter<SearchContract.View> {

    private val compositeDisposable = CompositeDisposable()

    private var view: SearchContract.View? = null

    override fun attachView(view: SearchContract.View) {
        this.view = view
        view.renderData(SearchViewState.CallToAction)
    }

    override fun detachView() {
        view = null
        compositeDisposable.dispose()
    }

    override fun getData(word: String, isOnline: Boolean) {
        compositeDisposable.add(repository.getData(word, isOnline)
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.main())
            .doOnSubscribe { view?.renderData(SearchViewState.Loading) }
            .subscribe({
                if (it.isEmpty()) {
                    view?.renderData(SearchViewState.EmptyResult)
                } else {
                    view?.renderData(SearchViewState.Success(it))
                }
            }, {
                view?.renderData(SearchViewState.Error(it))
            })
        )
    }
}