package com.manoelsrs.marvelchallenge.repository.local.favorite

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.manoelsrs.marvelchallenge.TestApp
import com.manoelsrs.marvelchallenge.model.Character
import com.manoelsrs.marvelchallenge.repository.local.favorite.entity.FavoriteDatabase
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApp::class, manifest = Config.NONE)
class LocalFavoriteRepositoryTest {

    private lateinit var database: FavoriteDatabase
    private lateinit var applicationContext: Context
    private lateinit var localFavoriteRepository: LocalFavoriteRepository

    private val character = Character(
        id = 1,
        name = "name test",
        description = "description",
        photo = "photo",
        photoExtension = ".jpg",
        hasSeries = true,
        hasComics = false
    )

    @Before
    fun before() {
        applicationContext = ApplicationProvider.getApplicationContext()
        database = Room.inMemoryDatabaseBuilder(applicationContext, FavoriteDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        localFavoriteRepository = LocalFavoriteRepository(database)
    }

    @After
    fun `close database`() {
        database.close()
    }

    @Test
    fun `insert one favorite and get this`() {
        localFavoriteRepository.insert(character)
        localFavoriteRepository.getFavorites()
            .map { assertEquals(character, it) }
    }

    @Test
    fun `insert favorites and get they`() {
        val characters = listOf(
            character,
            character.copy(id = 2),
            character.copy(id = 3),
            character.copy(id = 4),
            character.copy(id = 5)
        )
        with(localFavoriteRepository) {
            insert(character)
            insert(character.copy(id = 2)).test()
            insert(character.copy(id = 3)).test()
            insert(character.copy(id = 4)).test()
            insert(character.copy(id = 5)).test()
            getFavorites()
                .map { assertEquals(characters, it) }
        }
    }

    @Test
    fun `insert favorites, delete one and get they`() {
        val characters = listOf(
            character,
            character.copy(id = 2),
            character.copy(id = 3),
            character.copy(id = 4),
            character.copy(id = 5)
        )

        val expect = listOf(
            character,
            character.copy(id = 2),
            character.copy(id = 4),
            character.copy(id = 5)
        )
        with(localFavoriteRepository) {
            insert(character)
            insert(character.copy(id = 3)).test()
            insert(character.copy(id = 2)).test()
            insert(character.copy(id = 4)).test()
            insert(character.copy(id = 5)).test()
            delete(character.copy(id = 3)).test()
            getFavorites()
                .map { assertEquals(expect, it) }
        }
    }

    @Test
    fun `insert favorites and delete all`() {
        with(localFavoriteRepository) {
            insert(character)
            insert(character.copy(id = 2)).test()
            insert(character.copy(id = 3)).test()
            insert(character.copy(id = 4)).test()
            insert(character.copy(id = 5)).test()
            deleteAll()
            getFavorites()
                .map { assertEquals(listOf<Character>(), it) }
        }
    }
}