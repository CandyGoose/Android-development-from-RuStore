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
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
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
        observeAppDetails()
        loadAppDetails()
    }

    private fun loadAppDetails() {
        viewModelScope.launch {
            runCatching { appRepository.getAppDetails(appId) }
                .onFailure { _state.value = AppDetailsState.Error }
        }
    }

    private fun observeAppDetails() {
        viewModelScope.launch {
            appRepository.observeAppDetails(appId)
                .catch { _state.value = AppDetailsState.Error }
                .collect { appDetails ->
                    _state.value = AppDetailsState.Content(
                        appDetails = appDetails,
                        descriptionCollapsed = false
                    )
                }
        }
    }

    fun toggleWishlist() {
        viewModelScope.launch {
            appRepository.toggleWishlist(appId)
        }
    }
}
