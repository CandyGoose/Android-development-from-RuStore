package com.example.myapplication.applist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.HardcodedApps
import com.example.myapplication.domain.AppDetails
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class AppListUiState(
    val apps: List<AppDetails> = emptyList()
)

sealed interface AppListEvent {
    data class ShowLogoSnackbar(val message: String) : AppListEvent
}

class AppListViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        AppListUiState(
            apps = HardcodedApps.appList
        )
    )
    val uiState: StateFlow<AppListUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<AppListEvent>()
    val events: SharedFlow<AppListEvent> = _events.asSharedFlow()

    fun onLogoClick() {
        viewModelScope.launch {
            _events.emit(
                AppListEvent.ShowLogoSnackbar(
                    message = "Логотип RuStore нажат"
                )
            )
        }
    }
}

