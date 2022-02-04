package com.sshevtsov.translator.presentation.search

import com.sshevtsov.translator.domain.model.search.DataModel

sealed class SearchViewState {

    data class Success(val data: List<DataModel>) : SearchViewState()

    data class Error(val error: Throwable) : SearchViewState()

    object Loading : SearchViewState()

    object EmptyResult : SearchViewState()

    object CallToAction : SearchViewState()

}