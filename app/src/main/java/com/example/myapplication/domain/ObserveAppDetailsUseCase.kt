package com.example.myapplication.domain

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class ObserveAppDetailsUseCase @Inject constructor(
    private val appRepository: AppRepository
) {
    operator fun invoke(id: String): Flow<AppDetails> = appRepository.observeAppDetails(id)
}
