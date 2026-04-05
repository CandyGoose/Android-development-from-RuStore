package com.example.myapplication.domain

import javax.inject.Inject

class GetAppsUseCase @Inject constructor(
    private val appRepository: AppRepository
) {
    suspend operator fun invoke(): List<AppDetails> = appRepository.getApps()
}
