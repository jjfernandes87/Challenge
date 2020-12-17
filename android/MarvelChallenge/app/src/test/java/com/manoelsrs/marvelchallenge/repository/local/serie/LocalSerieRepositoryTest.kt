package com.manoelsrs.marvelchallenge.repository.local.serie

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.manoelsrs.marvelchallenge.TestApp
import com.manoelsrs.marvelchallenge.model.Data
import com.manoelsrs.marvelchallenge.repository.local.serie.entity.SerieDatabase
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApp::class, manifest = Config.NONE)
class LocalSerieRepositoryTest {

    private lateinit var database: SerieDatabase
    private lateinit var applicationContext: Context
    private lateinit var localSerieRepository: LocalSerieRepository

    private val serie = Data(
        id = 1,
        title = "title",
        photo = "photo",
        photoExtension = ".jpg"
    )

    @Before
    fun before() {
        applicationContext = ApplicationProvider.getApplicationContext()
        database = Room.inMemoryDatabaseBuilder(applicationContext, SerieDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        localSerieRepository = LocalSerieRepository(database)
    }

    @After
    fun `close database`() {
        database.close()
    }

    @Test
    fun `insert series and get counter`() {
        val series = listOf(
            serie,
            serie.copy(id = 2),
            serie.copy(id = 3),
            serie.copy(id = 4)
        )
        localSerieRepository.insert(series)
            .andThen(localSerieRepository.getSeriesCount())
            .test()
            .assertValue(4)
    }

    @Test
    fun `insert series and get series`() {
        val series = listOf(
            serie,
            serie.copy(id = 2),
            serie.copy(id = 3),
            serie.copy(id = 4)
        )

        localSerieRepository.insert(series)
            .andThen(localSerieRepository.getSeries().toSingle(series))
            .test()
            .assertValue(
                listOf(
                    serie,
                    serie.copy(id = 2),
                    serie.copy(id = 3),
                    serie.copy(id = 4)
                )
            )
    }

    @Test
    fun `insert series and deleteAll`() {
        val series = listOf(
            serie,
            serie.copy(id = 2),
            serie.copy(id = 3),
            serie.copy(id = 4)
        )

        localSerieRepository.insert(series)
            .andThen(localSerieRepository.deleteAll())
            .test()
            .assertNoValues()
    }
}