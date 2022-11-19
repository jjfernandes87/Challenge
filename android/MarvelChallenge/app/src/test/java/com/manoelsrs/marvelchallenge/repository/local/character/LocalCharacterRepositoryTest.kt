package com.manoelsrs.marvelchallenge.repository.local.character

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.manoelsrs.marvelchallenge.TestApp
import com.manoelsrs.marvelchallenge.model.Character
import com.manoelsrs.marvelchallenge.repository.local.character.entity.CharactersDatabase
import io.reactivex.Single
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApp::class, manifest = Config.NONE)
class LocalCharacterRepositoryTest {

    private lateinit var database: CharactersDatabase
    private lateinit var applicationContext: Context
    private lateinit var localCharacterRepository: LocalCharacterRepository

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
        database = Room.inMemoryDatabaseBuilder(applicationContext, CharactersDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        localCharacterRepository = LocalCharacterRepository(database)
    }

    @After
    fun `close database`() {
        database.close()
    }

    @Test
    fun `insert one character and get counter`() {
        Single.fromCallable {
            localCharacterRepository.insert(character)
        }
            .flatMap { localCharacterRepository.getCharactersCount() }
            .test()
            .assertValue(1)
    }

    @Test
    fun `insert characters and get counter`() {
        val characters = listOf(
            character,
            character.copy(id = 2),
            character.copy(id = 3),
            character.copy(id = 4),
            character.copy(id = 5)
        )
        Single.fromCallable {
            localCharacterRepository.insert(characters)
        }
            .flatMap { localCharacterRepository.getCharactersCount() }
            .test()
            .assertValue(5)
    }

    @Test
    fun `insert one character and get this`() {
        localCharacterRepository.insert(character)
        localCharacterRepository.getCharacters()
            .map { assertEquals(character, it) }
    }

    @Test
    fun `insert characters and get they`() {
        val characters = listOf(
            character,
            character.copy(id = 2),
            character.copy(id = 3),
            character.copy(id = 4),
            character.copy(id = 5)
        )
        localCharacterRepository.insert(characters)
        localCharacterRepository.getCharacters()
            .map { assertEquals(characters, it) }
    }

    @Test
    fun `insert characters, delete one and get they`() {
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
        with(localCharacterRepository) {
            insert(characters)
            delete(character.copy(id = 3))
            getCharacters()
                .map { assertEquals(expect, it) }
        }
    }

    @Test
    fun `insert characters and delete all`() {
        val characters = listOf(
            character,
            character.copy(id = 2),
            character.copy(id = 3),
            character.copy(id = 4),
            character.copy(id = 5)
        )
        with(localCharacterRepository) {
            insert(characters)
            deleteAll()
            getCharacters()
                .map { assertEquals(listOf<Character>(), it) }
        }
    }
}