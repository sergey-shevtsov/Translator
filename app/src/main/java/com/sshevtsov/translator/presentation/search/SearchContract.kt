package com.sshevtsov.translator.presentation.search

interface SearchContract {

    interface View {

        fun renderData(viewState: SearchViewState)

    }

    interface Presenter<V : View> {

        fun attachView(view: V)

        fun detachView()

        fun getData(word: String, isOnline: Boolean)

    }

}