package com.sshevtsov.translator.presentation.history

import com.sshevtsov.translator.domain.model.history.History

sealed class HistoryViewState {
    data class Success(val data: List<History>) : HistoryViewState()
    data class Error(val error: Throwable) : HistoryViewState()
    object Loading : HistoryViewState()
    object EmptyHistory : HistoryViewState()
}
