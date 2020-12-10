package br.com.mouzinho.marvelapp

import br.com.mouzinho.domain.entity.character.CharacterResult
import br.com.mouzinho.domain.interactor.character.GetCharacters
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

    @Mock
    lateinit var repository: CharacterRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        testSchedulerProvider = TestSchedulerProvider()
        getCharacters = GetCharacters(repository)
        viewModel = MainViewModel(getCharacters, testSchedulerProvider)
        testObserver = viewModel.stateObservable.test()
    }

    @Test
    fun testLoadingAndGetCharacters() {
        Mockito.`when`(repository.getCharacters(10, 0)).thenReturn(
            Observable.just<CharacterResult>(CharacterResult.Success(emptyList()))
                .startWith(CharacterResult.Loading)
        )
        viewModel.loadCharacters()
        testObserver.assertValueAt(0) { it.loading }
        testObserver.assertValueAt(1) { !it.loading && it.characters.isEmpty() }
    }
}