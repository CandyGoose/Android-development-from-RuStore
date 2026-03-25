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
    data class ShowErrorSnackbar(val message: String) : AppListEvent
}

@HiltViewModel
class AppListViewModel @Inject constructor(
    private val appRepository: AppRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AppListUiState())
    val uiState: StateFlow<AppListUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<AppListEvent>()
    val events: SharedFlow<AppListEvent> = _events.asSharedFlow()

    init {
        viewModelScope.launch {
            runCatching { appRepository.getApps() }
                .onSuccess { apps ->
                    _uiState.value = AppListUiState(apps = apps)
                }
                .onFailure {
                    _events.emit(
                        AppListEvent.ShowErrorSnackbar(
                            message = "Не удалось загрузить каталог приложений"
                        )
                    )
                }
        }
    }

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

