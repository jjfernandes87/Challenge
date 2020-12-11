package br.com.mouzinho.marvelapp

import androidx.paging.PagedList
import br.com.mouzinho.domain.entity.character.MarvelCharacter
import br.com.mouzinho.domain.interactor.character.GetCharacters
import br.com.mouzinho.domain.repository.character.CharacterRepository
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

    @Mock
    lateinit var repository: CharacterRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        testSchedulerProvider = TestSchedulerProvider()
        getCharacters = GetCharacters(repository)
        viewModel = CharactersViewModel(getCharacters, testSchedulerProvider)
        testObserver = viewModel.stateObservable.test()
    }

    @Test
    fun testLoadingAndGetCharacters() {
        val pagedList = mockPagedList(marvelCharactersMock())
        Mockito.`when`(repository.loadCharactersPagedList(any()))
            .thenReturn(Observable.just(pagedList))
        viewModel.loadCharacters()
        testObserver.assertValueAt(0) { it.loading }
        testObserver.assertValueAt(1) { !it.loading && it.characters != null }
    }

    @Test
    fun testErrorWhenLoadingCharacters() {
        Mockito.`when`(repository.loadCharactersPagedList(any()))
            .thenReturn(Observable.error(Throwable("Deu ruim")))
        viewModel.loadCharacters()
        testObserver.assertValueAt(0) { it.loading }
        testObserver.assertValueAt(1) { !it.loading && it.hasError && it.errorMessage == "Deu ruim" }
    }

    @Test
    fun testReloadCharacters() {
        val pagedList = mockPagedList(marvelCharactersMock())
        Mockito.`when`(repository.loadCharactersPagedList(any()))
            .thenReturn(Observable.just(pagedList))
        viewModel.reloadCharacters()
        testObserver.assertValueAt(0) { !it.loading && it.characters != null && !it.hasError && it.reloaded }
    }

    private fun marvelCharactersMock() = listOf(
        MarvelCharacter(null, "", 1, "", "", null, null),
        MarvelCharacter(null, "", 2, "", "", null, null)
    )

    private fun <T> mockPagedList(list: List<T>): PagedList<T> {
        val pagedList = Mockito.mock(PagedList::class.java) as PagedList<T>
        Mockito.`when`(pagedList.get(ArgumentMatchers.anyInt())).then { invocation ->
            val index = invocation.arguments.first() as Int
            list[index]
        }
        Mockito.`when`(pagedList.size).thenReturn(list.size)
        return pagedList
    }
}