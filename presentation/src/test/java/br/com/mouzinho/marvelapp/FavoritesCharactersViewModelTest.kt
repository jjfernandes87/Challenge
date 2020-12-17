package br.com.mouzinho.marvelapp

import br.com.mouzinho.domain.entity.character.MarvelCharacter
import br.com.mouzinho.domain.entity.favorite.FavoriteCharacter
import br.com.mouzinho.domain.interactor.character.GetMarvelCharacterInfo
import br.com.mouzinho.domain.interactor.character.ReloadCharacters
import br.com.mouzinho.domain.interactor.favorite.GetFavorites
import br.com.mouzinho.domain.interactor.favorite.RemoveFromFavorites
import br.com.mouzinho.domain.interactor.favorite.SearchFavorites
import br.com.mouzinho.domain.repository.character.MarvelCharacterRepository
import br.com.mouzinho.domain.repository.favorite.FavoritesMarvelCharacterRepository
import br.com.mouzinho.domain.resources.StringResources
import br.com.mouzinho.domain.scheduler.SchedulerProvider
import br.com.mouzinho.marvelapp.di.TestSchedulerProvider
import br.com.mouzinho.marvelapp.view.favorites.FavoritesCharactersViewModel
import br.com.mouzinho.marvelapp.view.favorites.FavoritesCharactersViewState
import com.nhaarman.mockitokotlin2.any
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class FavoritesCharactersViewModelTest {
    lateinit var viewModel: FavoritesCharactersViewModel
    lateinit var testObserver: TestObserver<FavoritesCharactersViewState>
    lateinit var testSchedulerProvider: SchedulerProvider
    lateinit var getCharacters: GetFavorites
    lateinit var getMarvelCharacterInfo: GetMarvelCharacterInfo
    lateinit var removeFromFavorites: RemoveFromFavorites
    lateinit var searchFavorites: SearchFavorites
    lateinit var reloadCharacter: ReloadCharacters

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
        getCharacters = GetFavorites(repositoryFavorites)
        getMarvelCharacterInfo = GetMarvelCharacterInfo(repositoryCharacters)
        removeFromFavorites = RemoveFromFavorites(repositoryFavorites)
        reloadCharacter = ReloadCharacters(repositoryCharacters)
        searchFavorites = SearchFavorites(repositoryFavorites)
        viewModel = FavoritesCharactersViewModel(
            getCharacters,
            getMarvelCharacterInfo,
            removeFromFavorites,
            searchFavorites,
            stringResources,
            testSchedulerProvider
        )
        testObserver = viewModel.stateObservable.test()
    }

    @Test
    fun testGetFavorites() {
        Mockito.`when`(repositoryFavorites.loadAllFavorites())
            .thenReturn(Observable.just(favoriteCharactersMock()))
        viewModel.loadFavorites()
        testObserver.assertValueAt(0) {
            it is FavoritesCharactersViewState.ShowFavorites && it.favorites == favoriteCharactersMock()
        }
    }

    @Test
    fun testSearchFavorites() {
        Mockito.`when`(repositoryFavorites.search("Spider Man"))
            .thenReturn(
                Observable.just(
                    favoriteCharactersMock().find { it.name == "Spider Man" }?.let { listOf(it) } ?: emptyList()
                )
            )
        viewModel.search("Spider Man")
        testObserver.assertValueAt(0) {
            it is FavoritesCharactersViewState.ShowFavorites && it.favorites.first().name == "Spider Man"
        }
    }

    @Test
    fun testRemoveFromFavorites() {
        val favorites = favoriteCharactersMock().toMutableList()
        Mockito.`when`(repositoryFavorites.removeFromFavorites(any<FavoriteCharacter>()))
            .thenReturn(Single.just(true))
        viewModel.removeFavorite(favorites[1])
        testObserver.assertValueAt(0) {
            it is FavoritesCharactersViewState.ShowRemovedMessage
        }
    }

    @Test
    fun testGoToMarvelCharacterDetails() {
        Mockito.`when`(repositoryCharacters.loadCharacterInfo(any()))
            .thenReturn(Single.just(marvelCharactersMock()[0]))
        viewModel.loadMarvelCharacterInfo(favoriteCharacterMock())
        testObserver.assertValueAt(0) {
            print(it)
            it is FavoritesCharactersViewState.ToggleLoading && it.isLoading
        }
        testObserver.assertValueAt(1) {
            print(it)
            it is FavoritesCharactersViewState.GoToDetails
        }
    }

    private fun marvelCharactersMock() = listOf(
        MarvelCharacter(null, "", 1, "Spider Man", "", null, null),
        MarvelCharacter(null, "", 2, "Iron Man", "", null, null)
    )

    private fun favoriteCharacterMock() = FavoriteCharacter(1, "Spider Man", "")

    private fun favoriteCharactersMock() = listOf(
        FavoriteCharacter(1, "Spider Man", ""),
        FavoriteCharacter(2, "Iron Man", ""),
        FavoriteCharacter(3, "Hulk", "")
    )
}