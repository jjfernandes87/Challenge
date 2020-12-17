package com.manoelsrs.marvelchallenge.repository.local.comic

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.manoelsrs.marvelchallenge.TestApp
import com.manoelsrs.marvelchallenge.model.Data
import com.manoelsrs.marvelchallenge.repository.local.comic.entity.ComicDatabase
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApp::class, manifest = Config.NONE)
class LocalComicRepositoryTest {

    private lateinit var database: ComicDatabase
    private lateinit var applicationContext: Context
    private lateinit var localComicRepository: LocalComicRepository

    private val comic = Data(
        id = 1,
        title = "title",
        photo = "photo",
        photoExtension = ".jpg"
    )

    @Before
    fun before() {
        applicationContext = ApplicationProvider.getApplicationContext()
        database = Room.inMemoryDatabaseBuilder(applicationContext, ComicDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        localComicRepository = LocalComicRepository(database)
    }

    @After
    fun `close database`() {
        database.close()
    }

    @Test
    fun `insert comics and get counter`() {
        val comics = listOf(
            comic,
            comic.copy(id = 2),
            comic.copy(id = 3),
            comic.copy(id = 4)
        )
            localComicRepository.insert(comics)
            .andThen(localComicRepository.getComicsCount())
            .test()
            .assertValue(4)
    }

    @Test
    fun `insert comics and get comics`() {
        val comics = listOf(
            comic,
            comic.copy(id = 2),
            comic.copy(id = 3),
            comic.copy(id = 4)
        )

            localComicRepository.insert(comics)
            .andThen(localComicRepository.getComics().toSingle(comics))
            .test()
            .assertValue(
                listOf(
                    comic,
                    comic.copy(id = 2),
                    comic.copy(id = 3),
                    comic.copy(id = 4)
                )
            )
    }

    @Test
    fun `insert comics and deleteAll`() {
        val comics = listOf(
            comic,
            comic.copy(id = 2),
            comic.copy(id = 3),
            comic.copy(id = 4)
        )

            localComicRepository.insert(comics)
            .andThen(localComicRepository.deleteAll())
            .test()
            .assertNoValues()
    }
}