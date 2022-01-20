package com.sshevtsov.translator.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sshevtsov.translator.domain.model.DataModel
import com.sshevtsov.translator.domain.repositories.Repository
import com.sshevtsov.translator.util.DispatcherProvider
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class SearchViewModel(
    private val repository: Repository<DataModel>,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val _viewState = MutableLiveData<SearchViewState>(SearchViewState.CallToAction)

    val viewState: LiveData<SearchViewState> get() = _viewState

    fun getData(word: String, isOnline: Boolean) {

        setLoadingState()
        cancelJob()

        viewModelScope.launch {
            repository.getData(word, isOnline)
                .flowOn(dispatcherProvider.io())
                .catch { handleError(it) }
                .collect {
                    if (it.isEmpty()) setEmptyResultState()
                    else setSuccessState(it)
                }
        }
    }

    private fun setSuccessState(data: List<DataModel>) {
        _viewState.value = SearchViewState.Success(data)
    }

    private fun setLoadingState() {
        _viewState.value = SearchViewState.Loading
    }

    private fun setEmptyResultState() {
        _viewState.value = SearchViewState.EmptyResult
    }

    private fun handleError(error: Throwable) {
        _viewState.value = SearchViewState.Error(error)
    }

    private fun cancelJob() {
        viewModelScope.coroutineContext.cancelChildren()
    }

    override fun onCleared() {
        cancelJob()
    }
}

