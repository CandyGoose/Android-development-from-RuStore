package com.example.myapplication.domain

import javax.inject.Inject

class GetAppByIdUseCase @Inject constructor(
    private val appRepository: AppRepository
) {
    operator fun invoke(id: String): AppDetails? = appRepository.getAppById(id)
}
