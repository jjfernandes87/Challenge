package br.com.mouzinho.marvelapp

import androidx.paging.PagedList
import br.com.mouzinho.domain.entity.character.MarvelCharacter
import br.com.mouzinho.domain.interactor.character.GetCharacters
import br.com.mouzinho.domain.interactor.character.ReloadCharacters
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
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class LoadCharactersTest {
    lateinit var viewModel: CharactersViewModel
    lateinit var testObserver: TestObserver<CharactersViewState>
    lateinit var testSchedulerProvider: SchedulerProvider
    lateinit var getCharacters: GetCharacters
    lateinit var updateFavorite: UpdateFavorite
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
        getCharacters = GetCharacters(repositoryCharacters)
        updateFavorite = UpdateFavorite(repositoryFavorites)
        reloadCharacter = ReloadCharacters(repositoryCharacters)
        viewModel = CharactersViewModel(getCharacters, updateFavorite, reloadCharacter, testSchedulerProvider, stringResources)
        testObserver = viewModel.stateObservable.test()
    }

    @Test
    fun testLoadingAndGetCharacters() {
        val pagedList = mockPagedList(marvelCharactersMock())
        Mockito.`when`(repositoryCharacters.loadCharactersPagedList(any()))
            .thenReturn(Observable.just(pagedList))
        viewModel.loadCharacters()
        testObserver.assertValueAt(0) {
            it is CharactersViewState.Loading
        }
        testObserver.assertValueAt(1) {
            it is CharactersViewState.CharactersLoaded
        }
    }

    @Test
    fun testErrorWhenLoadingCharacters() {
        Mockito.`when`(repositoryCharacters.loadCharactersPagedList(any()))
            .thenReturn(Observable.error(Throwable("Deu ruim")))
        viewModel.loadCharacters()
        testObserver.assertValueAt(0) {
            it is CharactersViewState.Loading
        }
        testObserver.assertValueAt(1) {
            it is CharactersViewState.Error
        }
    }

    private fun marvelCharactersMock() = listOf(
        MarvelCharacter(null, "", 1, "", "", null, null),
        MarvelCharacter(null, "", 2, "", "", null, null)
    )

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