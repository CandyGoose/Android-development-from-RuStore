package com.example.myapplication.domain

import javax.inject.Inject

class ToggleWishlistUseCase @Inject constructor(
    private val appRepository: AppRepository
) {
    suspend operator fun invoke(id: String) = appRepository.toggleWishlist(id)
}
