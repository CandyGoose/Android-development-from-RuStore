package com.example.myapplication.data

import com.example.myapplication.domain.AppDetails
import com.example.myapplication.domain.Category

object HardcodedApps {

    private val screenshotUrls = listOf(
        "https://static.rustore.ru/imgproxy/-y8kd-4B6MQ-1OKbAbnoAIMZAzvoMMG9dSiHMpFaTBc/preset:web_scr_lnd_335/plain/https://static.rustore.ru/apk/393868735/content/SCREENSHOT/dfd33017-e90d-4990-aa8c-6f159d546788.jpg@webp",
        "https://static.rustore.ru/imgproxy/dZCvNtRKKFpzOmGlTxLszUPmwi661IhXynYZGsJQvLw/preset:web_scr_lnd_335/plain/https://static.rustore.ru/apk/393868735/content/SCREENSHOT/60ec4cbc-dcf6-4e69-aa6f-cc2da7de1af6.jpg@webp",
        "https://static.rustore.ru/imgproxy/g5whSI1uNqaL2TUO7TFfM8M63vXpWXNCm2vlX4Ahvc4/preset:web_scr_lnd_335/plain/https://static.rustore.ru/apk/393868735/content/SCREENSHOT/c2dde8bc-c4ab-482a-80a5-2789149f598d.jpg@webp"
    )

    private const val ICON_URL_RUSTORE = "https://static.rustore.ru/imgproxy/APsbtHxkVa4MZ0DXjnIkSwFQ_KVIcqHK9o3gHY6pvOQ/preset:web_app_icon_62/plain/https://static.rustore.ru/apk/393868735/content/ICON/3f605e3e-f5b3-434c-af4d-77bc5f38820e.png@webp"

    val appList: List<AppDetails> = listOf(
        AppDetails(
            id = "fa2e31b8-1234-4cf7-9914-108a170a1b01",
            name = "Гильдия Героев: Экшен ММО РПГ",
            developer = "VK Play",
            category = Category.GAME,
            ageRating = 12,
            size = 223.7f,
            iconUrl = ICON_URL_RUSTORE,
            screenshotUrlList = screenshotUrls,
            description = "Легендарный рейд героев в Фэнтези РПГ. Станьте героем гильдии и сразите мастера подземелья!"
        ),
        AppDetails(
            id = "app-2",
            name = "ВКонтакте",
            developer = "VK",
            category = Category.SOCIAL,
            ageRating = 12,
            size = 156.2f,
            iconUrl = ICON_URL_RUSTORE,
            screenshotUrlList = screenshotUrls,
            description = "Общайтесь с друзьями, смотрите видео и слушайте музыку."
        ),
        AppDetails(
            id = "app-3",
            name = "Яндекс Карты",
            developer = "Яндекс",
            category = Category.MAPS,
            ageRating = 3,
            size = 89.5f,
            iconUrl = ICON_URL_RUSTORE,
            screenshotUrlList = screenshotUrls,
            description = "Навигатор, пробки и поиск мест рядом."
        )
    )

    fun getById(id: String): AppDetails? = appList.find { it.id == id }
}
