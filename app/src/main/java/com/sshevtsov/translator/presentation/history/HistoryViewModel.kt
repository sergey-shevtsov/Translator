package com.sshevtsov.translator.presentation.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sshevtsov.translator.domain.model.history.History
import com.sshevtsov.translator.domain.repositories.RepositoryLocal
import com.sshevtsov.translator.util.DispatcherProvider
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val repositoryLocal: RepositoryLocal<History>,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val _viewState = MutableStateFlow<HistoryViewState>(HistoryViewState.Loading)
    val viewState: StateFlow<HistoryViewState> get() = _viewState.asStateFlow()

    fun fetchHistory() {
        viewModelScope.launch {
            emitLoadingState()
            repositoryLocal.getData()
                .flowOn(dispatcherProvider.io())
                .catch { e ->
                    emitErrorState(e)
                }
                .collect { history ->
                    if (history.isEmpty()) {
                        emitEmptyHistoryState()
                    } else {
                        emitSuccessState(history)
                    }
                }
        }
    }

    private suspend fun emitLoadingState() {
        _viewState.emit(HistoryViewState.Loading)
    }

    private suspend fun emitEmptyHistoryState() {
        _viewState.emit(HistoryViewState.EmptyHistory)
    }

    private suspend fun emitSuccessState(data: List<History>) {
        _viewState.emit(HistoryViewState.Success(data))
    }

    private suspend fun emitErrorState(e: Throwable) {
        _viewState.emit(HistoryViewState.Error(e))
    }
}