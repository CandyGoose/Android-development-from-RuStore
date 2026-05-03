package com.example.myapplication.domain

import javax.inject.Inject

class GetAppDetailsUseCase @Inject constructor(
    private val appRepository: AppRepository
) {
    suspend operator fun invoke(id: String): AppDetails = appRepository.getAppDetails(id)
}
