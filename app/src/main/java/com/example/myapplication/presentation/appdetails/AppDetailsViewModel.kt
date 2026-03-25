package com.example.myapplication.presentation.appdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.appdetails.AppDetailsState
import com.example.myapplication.domain.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class AppDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val appRepository: AppRepository
) : ViewModel() {

    private val appId: String = savedStateHandle.get<String>("appId").orEmpty()

    private val _state = MutableStateFlow<AppDetailsState>(AppDetailsState.Loading)
    val state: StateFlow<AppDetailsState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            runCatching { loadAppDetails() }
                .onSuccess { details ->
                    _state.value = AppDetailsState.Content(
                        appDetails = details,
                        descriptionCollapsed = false
                    )
                }
                .onFailure { _state.value = AppDetailsState.Error }
        }
    }

    private suspend fun loadAppDetails() = appRepository.getAppDetails(appId)
}
