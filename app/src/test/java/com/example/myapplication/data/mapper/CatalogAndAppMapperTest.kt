package com.example.myapplication.data.mapper

import com.example.myapplication.data.dto.AppDto
import com.example.myapplication.data.dto.CatalogAppDto
import com.example.myapplication.domain.Category
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class CatalogAndAppMapperTest {

    @Test
    fun `CatalogAppDto maps all fields to domain`() {
        val dto = CatalogAppDto(
            id = "id1",
            name = "Name",
            description = "About",
            category = "Игры",
            iconUrl = "https://i",
            developer = "Studio",
            ageRating = 16,
            size = 99.5f,
            screenshotUrlList = listOf("a", "b")
        )

        val domain = dto.toDomain()

        assertEquals("id1", domain.id)
        assertEquals("Name", domain.name)
        assertEquals("Studio", domain.developer)
        assertEquals(Category.GAME, domain.category)
        assertEquals(16, domain.ageRating)
        assertEquals(99.5f, domain.size, 0f)
        assertEquals("https://i", domain.iconUrl)
        assertEquals(listOf("a", "b"), domain.screenshotUrlList)
        assertEquals("About", domain.description)
    }

    @Test
    fun `CatalogAppDto uses defaults for nullable fields`() {
        val dto = CatalogAppDto(
            id = "x",
            name = "Y",
            description = "Z",
            category = "Неизвестная категория",
            iconUrl = "u",
            developer = null,
            ageRating = null,
            size = null,
            screenshotUrlList = null
        )

        val domain = dto.toDomain()

        assertEquals("", domain.developer)
        assertEquals(0, domain.ageRating)
        assertEquals(0f, domain.size, 0f)
        assertTrue(domain.screenshotUrlList.isEmpty())
        assertEquals(Category.APP, domain.category)
    }

    @Test
    fun `CatalogAppDto maps category by display name`() {
        val dto = CatalogAppDto(
            id = "1",
            name = "n",
            description = "d",
            category = "Социальные сети",
            iconUrl = "i"
        )

        assertEquals(Category.SOCIAL, dto.toDomain().category)
    }

    @Test
    fun `AppDto maps enum category string to domain`() {
        val dto = AppDto(
            id = "app-1",
            name = "Maps",
            developer = "Yandex",
            category = "MAPS",
            ageRating = 3,
            size = 12f,
            iconUrl = "icon",
            screenshotUrlList = listOf("s"),
            description = "Nav"
        )

        val domain = dto.toDomain()

        assertEquals(Category.MAPS, domain.category)
        assertEquals("Yandex", domain.developer)
        assertEquals(listOf("s"), domain.screenshotUrlList)
        assertEquals("Nav", domain.description)
    }

    @Test
    fun `AppDto preserves numeric and string fields`() {
        val dto = AppDto(
            id = "z",
            name = "App",
            developer = "Dev",
            category = "UTILITIES",
            ageRating = 18,
            size = 0.5f,
            iconUrl = "url",
            screenshotUrlList = emptyList(),
            description = "text"
        )

        val domain = dto.toDomain()

        assertEquals(18, domain.ageRating)
        assertEquals(0.5f, domain.size, 0f)
        assertEquals(emptyList<String>(), domain.screenshotUrlList)
    }
}
