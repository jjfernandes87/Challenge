package br.com.mouzinho.marvelapp.view.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import br.com.mouzinho.domain.entity.character.CharacterResult
import br.com.mouzinho.domain.interactor.character.GetCharacters
import br.com.mouzinho.domain.scheduler.SchedulerProvider
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.PublishSubject

class MainViewModel @ViewModelInject constructor(
    private val getCharacters: GetCharacters,
    private val schedulerProvider: SchedulerProvider
) : ViewModel() {
    val stateObservable: Observable<MainState> by lazy { statePublisher.hide() }

    private val statePublisher by lazy { PublishSubject.create<MainState>() }
    private val initialState = MainState()
    private val disposables = CompositeDisposable()

    fun loadCharacters() {
        getCharacters(10, 0)
            .observeOn(schedulerProvider.ui())
            .subscribe { result ->
                when (result) {
                    is CharacterResult.Success -> {
                        statePublisher.onNext(
                            initialState.copy(
                                loading = false,
                                characters = result.data
                            )
                        )
                    }
                    is CharacterResult.Failure -> {
                        statePublisher.onNext(
                            initialState.copy(
                                loading = false,
                                hasError = true,
                                errorMessage = result.throwable.message
                            )
                        )
                    }
                    is CharacterResult.Loading -> {
                        statePublisher.onNext(initialState)
                    }
                }
            }
            .addTo(disposables)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}