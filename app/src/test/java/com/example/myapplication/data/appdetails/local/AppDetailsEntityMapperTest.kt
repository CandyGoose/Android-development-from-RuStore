package com.example.myapplication.data.appdetails.local

import com.example.myapplication.domain.AppDetails
import com.example.myapplication.domain.Category
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class AppDetailsEntityMapperTest {

    private val mapper = AppDetailsEntityMapper()

    @Test
    fun `toEntity then toDomain round trip preserves fields`() {
        val domain = AppDetails(
            id = "r1",
            name = "Round",
            developer = "Dev",
            category = Category.MUSIC,
            ageRating = 10,
            size = 3.14f,
            iconUrl = "icon",
            screenshotUrlList = listOf("a.png", "b.png"),
            description = "Long text",
            isInWishlist = true
        )

        val back = mapper.toDomain(mapper.toEntity(domain))

        assertEquals(domain, back)
    }

    @Test
    fun `toDomain parses screenshot json`() {
        val entity = AppDetailsEntity(
            id = "1",
            name = "n",
            developer = "d",
            category = Category.APP,
            ageRating = 0,
            size = 0f,
            iconUrl = "i",
            screenshotUrlsJson = """["x","y"]""",
            description = "desc",
            isInWishlist = false
        )

        val domain = mapper.toDomain(entity)

        assertEquals(listOf("x", "y"), domain.screenshotUrlList)
    }

    @Test
    fun `toDomain returns empty screenshots for blank json`() {
        val entity = minimalEntity(screenshotUrlsJson = "   ")

        assertTrue(mapper.toDomain(entity).screenshotUrlList.isEmpty())
    }

    @Test
    fun `toEntity serializes screenshots to json`() {
        val domain = AppDetails(
            id = "1",
            name = "n",
            developer = "d",
            category = Category.GAME,
            ageRating = 0,
            size = 0f,
            iconUrl = "i",
            screenshotUrlList = listOf("one"),
            description = "d"
        )

        val json = mapper.toEntity(domain).screenshotUrlsJson

        assertEquals("""["one"]""", json)
    }

    @Test
    fun `wishlist flag is preserved in both directions`() {
        val withWish = minimalDomain(isInWishlist = true)
        assertEquals(true, mapper.toDomain(mapper.toEntity(withWish)).isInWishlist)

        val withoutWish = minimalDomain(isInWishlist = false)
        assertEquals(false, mapper.toDomain(mapper.toEntity(withoutWish)).isInWishlist)
    }

    private fun minimalEntity(screenshotUrlsJson: String = "[]") = AppDetailsEntity(
        id = "1",
        name = "n",
        developer = "d",
        category = Category.APP,
        ageRating = 0,
        size = 0f,
        iconUrl = "i",
        screenshotUrlsJson = screenshotUrlsJson,
        description = "d",
        isInWishlist = false
    )

    private fun minimalDomain(isInWishlist: Boolean) = AppDetails(
        id = "w",
        name = "n",
        developer = "d",
        category = Category.NEWS,
        ageRating = 0,
        size = 0f,
        iconUrl = "i",
        screenshotUrlList = emptyList(),
        description = "d",
        isInWishlist = isInWishlist
    )
}
