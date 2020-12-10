package br.com.mouzinho.marvelapp

import br.com.mouzinho.data.entity.character.ApiCharacterResponse
import br.com.mouzinho.data.entity.character.ApiData
import br.com.mouzinho.data.entity.character.ApiItem
import br.com.mouzinho.data.entity.character.ApiMarvelCharacter
import br.com.mouzinho.data.mapper.*
import br.com.mouzinho.data.network.ApiService
import br.com.mouzinho.data.repository.character.CharacterRepositoryImpl
import br.com.mouzinho.domain.entity.character.Item
import br.com.mouzinho.domain.entity.character.MarvelCharacter
import br.com.mouzinho.domain.interactor.character.GetCharacters
import br.com.mouzinho.domain.mapper.Mapper
import br.com.mouzinho.domain.repository.character.CharacterRepository
import br.com.mouzinho.domain.scheduler.SchedulerProvider
import br.com.mouzinho.marvelapp.di.TestSchedulerProvider
import br.com.mouzinho.marvelapp.view.main.MainState
import br.com.mouzinho.marvelapp.view.main.MainViewModel
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class LoadCharactersTest {
    lateinit var viewModel: MainViewModel
    lateinit var testObserver: TestObserver<MainState>
    lateinit var testSchedulerProvider: SchedulerProvider
    lateinit var getCharacters: GetCharacters
    lateinit var mapper: Mapper<ApiMarvelCharacter, MarvelCharacter>
    lateinit var itemMapper: Mapper<ApiItem, Item>

    @Mock
    lateinit var apiService: ApiService

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        itemMapper = ItemMapper()
        mapper =
            CharacterMapper(ComicsMapper(itemMapper), SeriesMapper(itemMapper), ThumbnailMapper())
        testSchedulerProvider = TestSchedulerProvider()
        getCharacters = GetCharacters(CharacterRepositoryImpl(apiService, mapper))
        viewModel = MainViewModel(getCharacters, testSchedulerProvider)
        testObserver = viewModel.stateObservable.test()
    }

    @Test
    fun testLoadingAndGetCharacters() {
        Mockito.`when`(apiService.getCharacters(20, 0)).thenReturn(
            Observable.just(
                ApiCharacterResponse(
                    null,
                    null,
                    null,
                    null,
                    ApiData(20, 20, 0, emptyList(), 20),
                    null,
                    null
                )
            )
        )
        viewModel.loadCharacters()
        testObserver.assertValueAt(0) { it.loading }
    }
}