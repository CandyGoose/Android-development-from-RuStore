package com.example.myapplication.data.appdetails.local

import com.example.myapplication.domain.AppDetails
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

class AppDetailsEntityMapper @Inject constructor() {
    private val gson = Gson()
    private val listType = object : TypeToken<List<String>>() {}.type

    fun toEntity(domain: AppDetails): AppDetailsEntity = AppDetailsEntity(
        id = domain.id,
        name = domain.name,
        developer = domain.developer,
        category = domain.category,
        ageRating = domain.ageRating,
        size = domain.size,
        iconUrl = domain.iconUrl,
        screenshotUrlsJson = gson.toJson(domain.screenshotUrlList),
        description = domain.description
    )

    fun toDomain(entity: AppDetailsEntity): AppDetails = AppDetails(
        id = entity.id,
        name = entity.name,
        developer = entity.developer,
        category = entity.category,
        ageRating = entity.ageRating,
        size = entity.size,
        iconUrl = entity.iconUrl,
        screenshotUrlList = parseScreenshots(entity.screenshotUrlsJson),
        description = entity.description
    )

    private fun parseScreenshots(json: String): List<String> {
        if (json.isBlank()) return emptyList()
        return gson.fromJson(json, listType) ?: emptyList()
    }
}
