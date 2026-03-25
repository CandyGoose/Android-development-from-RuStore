package com.example.myapplication.domain

enum class Category(val displayName: String) {
    APP("Приложения"),
    GAME("Игры"),
    PRODUCTIVITY("Производительность"),
    SOCIAL("Социальные сети"),
    EDUCATION("Образование"),
    ENTERTAINMENT("Развлечения"),
    MUSIC("Музыка"),
    VIDEO("Видео"),
    PHOTOGRAPHY("Фотография"),
    HEALTH("Здоровье"),
    SPORTS("Спорт"),
    NEWS("Новости"),
    BOOKS("Книги"),
    BUSINESS("Бизнес"),
    FINANCE("Финансы"),
    TRAVEL("Путешествия"),
    MAPS("Карты"),
    FOOD("Еда"),
    SHOPPING("Покупки"),
    UTILITIES("Утилиты")
    ;

    companion object {
        fun fromServerCategory(raw: String): Category {
            val normalized = raw.trim()
            return entries.firstOrNull { it.displayName == normalized } ?: run {
                when {
                    normalized.contains("Игры", ignoreCase = true) -> GAME
                    normalized.contains("Производительность", ignoreCase = true) -> PRODUCTIVITY
                    normalized.contains("Социаль", ignoreCase = true) -> SOCIAL
                    normalized.contains("Образование", ignoreCase = true) -> EDUCATION
                    normalized.contains("Развлеч", ignoreCase = true) -> ENTERTAINMENT
                    normalized.contains("Музык", ignoreCase = true) -> MUSIC
                    normalized.contains("Фото", ignoreCase = true) -> PHOTOGRAPHY
                    normalized.contains("Видео", ignoreCase = true) -> VIDEO
                    normalized.contains("Здоров", ignoreCase = true) -> HEALTH
                    normalized.contains("Спорт", ignoreCase = true) -> SPORTS
                    normalized.contains("Новости", ignoreCase = true) -> NEWS
                    normalized.contains("Книг", ignoreCase = true) -> BOOKS
                    normalized.contains("Бизнес", ignoreCase = true) -> BUSINESS
                    normalized.contains("Финанс", ignoreCase = true) -> FINANCE
                    normalized.contains("Путеше", ignoreCase = true) -> TRAVEL
                    normalized.contains("Карт", ignoreCase = true) -> MAPS
                    normalized.contains("Ед", ignoreCase = true) -> FOOD
                    normalized.contains("Шопинг", ignoreCase = true) || normalized.contains("Покупк", ignoreCase = true) -> SHOPPING
                    normalized.contains("Утилит", ignoreCase = true) -> UTILITIES
                    else -> APP
                }
            }
        }
    }
}
