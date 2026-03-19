package com.example.myapplication.presentation.applist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.AppDetails
import com.example.myapplication.domain.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
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

@HiltViewModel
class AppListViewModel @Inject constructor(
    private val appRepository: AppRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        AppListUiState(
            apps = appRepository.getApps()
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

