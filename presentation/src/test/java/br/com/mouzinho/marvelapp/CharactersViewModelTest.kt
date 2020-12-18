package br.com.mouzinho.marvelapp

import androidx.paging.PagedList
import br.com.mouzinho.domain.entity.character.MarvelCharacter
import br.com.mouzinho.domain.entity.character.MarvelCharacterPagingResult
import br.com.mouzinho.domain.entity.favorite.FavoriteCharacter
import br.com.mouzinho.domain.interactor.character.GetCharacters
import br.com.mouzinho.domain.interactor.character.ReloadCharacters
import br.com.mouzinho.domain.interactor.character.SearchCharacters
import br.com.mouzinho.domain.interactor.character.UpdateFavorite
import br.com.mouzinho.domain.repository.character.MarvelCharacterRepository
import br.com.mouzinho.domain.repository.favorite.FavoritesMarvelCharacterRepository
import br.com.mouzinho.domain.resources.StringResources
import br.com.mouzinho.domain.scheduler.SchedulerProvider
import br.com.mouzinho.marvelapp.di.TestSchedulerProvider
import br.com.mouzinho.marvelapp.view.characters.CharactersViewModel
import br.com.mouzinho.marvelapp.view.characters.CharactersViewState
import com.nhaarman.mockitokotlin2.any
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class CharactersViewModelTest {
    lateinit var viewModel: CharactersViewModel
    lateinit var testObserver: TestObserver<CharactersViewState>
    lateinit var testSchedulerProvider: SchedulerProvider
    lateinit var getCharacters: GetCharacters
    lateinit var updateFavorite: UpdateFavorite
    lateinit var reloadCharacter: ReloadCharacters
    lateinit var searchCharacters: SearchCharacters

    @Mock
    lateinit var repositoryCharacters: MarvelCharacterRepository
    @Mock
    lateinit var repositoryFavorites: FavoritesMarvelCharacterRepository
    @Mock
    lateinit var stringResources: StringResources

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        testSchedulerProvider = TestSchedulerProvider()
        getCharacters = GetCharacters(repositoryCharacters)
        updateFavorite = UpdateFavorite(repositoryFavorites)
        reloadCharacter = ReloadCharacters(repositoryCharacters)
        searchCharacters = SearchCharacters(repositoryCharacters)
        viewModel = CharactersViewModel(
            getCharacters,
            updateFavorite,
            reloadCharacter,
            searchCharacters,
            testSchedulerProvider,
            stringResources
        )
        testObserver = viewModel.stateObservable.test()
    }

    @Test
    fun testGetCharacters() {
        val pagedList = mockPagedList(marvelCharactersMock())
        Mockito.`when`(repositoryCharacters.loadCharactersPagedList(any()))
            .thenReturn(Observable.just(MarvelCharacterPagingResult.Created(pagedList)))
        viewModel.loadCharacters()
        testObserver.assertValueAt(0) {
            print(it)
            it is CharactersViewState.CharactersLoaded
        }
    }

    @Test
    fun testLoadingCharacters() {
        Mockito.`when`(repositoryCharacters.loadCharactersPagedList(any()))
            .thenReturn(Observable.just(MarvelCharacterPagingResult.Loading(true)))
        viewModel.loadCharacters()
        testObserver.assertValueAt(0) {
            print(it)
            it is CharactersViewState.ToggleLoading && it.isLoading
        }
    }

    @Test
    fun testLoadedCharacters() {
        Mockito.`when`(repositoryCharacters.loadCharactersPagedList(any()))
            .thenReturn(Observable.just(MarvelCharacterPagingResult.Loading(false)))
        viewModel.loadCharacters()
        testObserver.assertValueAt(0) {
            print(it)
            it is CharactersViewState.ToggleLoading && !it.isLoading
        }
    }

    @Test
    fun testShowEmptyView() {
        Mockito.`when`(repositoryCharacters.loadCharactersPagedList(any()))
            .thenReturn(Observable.just(MarvelCharacterPagingResult.ListCondition(true)))
        viewModel.loadCharacters()
        testObserver.assertValueAt(0) {
            print(it)
            it is CharactersViewState.ToggleEmptyView && it.isEmpty
        }
    }

    @Test
    fun testNotShowEmptyView() {
        Mockito.`when`(repositoryCharacters.loadCharactersPagedList(any()))
            .thenReturn(Observable.just(MarvelCharacterPagingResult.ListCondition(false)))
        viewModel.loadCharacters()
        testObserver.assertValueAt(0) {
            print(it)
            it is CharactersViewState.ToggleEmptyView && !it.isEmpty
        }
    }

    @Test
    fun testErrorWhenLoadingCharacters() {
        Mockito.`when`(repositoryCharacters.loadCharactersPagedList(any()))
            .thenReturn(Observable.just(MarvelCharacterPagingResult.Error(Throwable("Deu ruim"))))
        viewModel.loadCharacters()
        testObserver.assertValueAt(0) {
            it is CharactersViewState.Error
        }
    }

    @Test
    fun testSaveAsFavorite() {
        Mockito.`when`(repositoryFavorites.saveAsFavorite(any())).thenReturn(Single.just(true))
        Mockito.`when`(repositoryFavorites.getFavoriteById(any())).thenReturn(Single.just(emptyList()))
        viewModel.updateCharacterFromFavorites(marvelCharacterMock())
        testObserver.assertValueAt(0) {
            it is CharactersViewState.FavoriteSaved && it.character.id == marvelCharacterMock().id
        }
    }

    @Test
    fun testRemoveFromFavorites() {
        Mockito.`when`(repositoryFavorites.removeFromFavorites(any<MarvelCharacter>())).thenReturn(Single.just(true))
        Mockito.`when`(repositoryFavorites.getFavoriteById(any())).thenReturn(Single.just(listOf(favoriteCharacterMock())))
        viewModel.updateCharacterFromFavorites(marvelCharacterMock())
        testObserver.assertValueAt(0) {
            it is CharactersViewState.FavoriteRemoved && it.character.id == marvelCharacterMock().id
        }
    }

    @Test
    fun testUpdateFavoriteError() {
        Mockito.`when`(repositoryFavorites.removeFromFavorites(any<MarvelCharacter>())).thenReturn(Single.just(true))
        Mockito.`when`(repositoryFavorites.getFavoriteById(any())).thenReturn(Single.just(emptyList()))
        Mockito.`when`(stringResources.genericErrorMessage).thenReturn("Error")
        Mockito.`when`(stringResources.updateFavoriteError).thenReturn("Error")
        viewModel.updateCharacterFromFavorites(marvelCharacterMock())
        testObserver.assertValueAt(0) {
            it is CharactersViewState.FavoriteUpdateError && it.message == "Error"
        }
    }

    @Test
    fun testReloadCharacters() {
        val pagedList = mockPagedList(marvelCharactersMock())
        Mockito.`when`(repositoryCharacters.loadCharactersPagedList(any()))
            .thenReturn(Observable.just(MarvelCharacterPagingResult.Created(pagedList)))
        Mockito.`when`(repositoryCharacters.reload()).then {
            viewModel.loadCharacters() // Reload calls load again
        }
        viewModel.loadCharacters()
        viewModel.reloadCharacters()
        // First result of loadCharacters() call
        testObserver.assertValueAt(0) {
            it is CharactersViewState.CharactersLoaded
        }
        // Second result of reloadCharacters() call
        testObserver.assertValueAt(1) {
            it is CharactersViewState.CharactersLoaded
        }
    }

    @Test
    fun testSearchCharacter() {
        val pagedList = mockPagedList(marvelCharactersMock().subList(0, 1))
        Mockito.`when`(repositoryCharacters.loadCharactersPagedList(any()))
            .thenReturn(Observable.just(MarvelCharacterPagingResult.Created(pagedList)))
        Mockito.`when`(repositoryCharacters.sendSearchNameToPagingSource("Spider Man")).then {
            viewModel.loadCharacters()
        }
        viewModel.search("Spider Man")
        testObserver.assertValueAt(0) {
            it is CharactersViewState.CharactersLoaded && it.characters.first().name == "Spider Man"
        }
    }

    private fun marvelCharactersMock() = listOf(
        MarvelCharacter(null, "", 1, "Spider Man", "", null, null),
        MarvelCharacter(null, "", 2, "Iron Man", "", null, null)
    )

    private fun marvelCharacterMock() = MarvelCharacter(null, "", 1, "", "", null, null)
    private fun favoriteCharacterMock() = FavoriteCharacter(1, "", "")

    fun <T> mockPagedList(list: List<T>): PagedList<T> {
        val pagedList = Mockito.mock(PagedList::class.java) as PagedList<T>
        Mockito.`when`(pagedList.get(ArgumentMatchers.anyInt())).then { invocation ->
            val index = invocation.arguments.first() as Int
            list[index]
        }
        Mockito.`when`(pagedList.size).thenReturn(list.size)
        return pagedList
    }
}