package br.com.mouzinho.marvelapp.view.characters

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import br.com.mouzinho.domain.entity.character.MarvelCharacter
import br.com.mouzinho.domain.interactor.character.GetCharacters
import br.com.mouzinho.domain.scheduler.SchedulerProvider
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.PublishSubject

class CharactersViewModel @ViewModelInject constructor(
    private val getCharacters: GetCharacters,
    private val schedulerProvider: SchedulerProvider
) : ViewModel() {
    val stateObservable: Observable<CharactersViewState> by lazy { statePublisher.hide() }

    private val statePublisher by lazy { PublishSubject.create<CharactersViewState>() }
    private val initialState = CharactersViewState()
    private val disposables = CompositeDisposable()

    fun loadCharacters(pageSize: Int = 20) {
        getCharacters(pageSize)
            .observeOn(schedulerProvider.ui())
            .doOnSubscribe { statePublisher.onNext(initialState) }
            .subscribe(::onCharactersReceived, ::onError)
            .addTo(disposables)
    }

    fun reloadCharacters(pageSize: Int = 20) {
        getCharacters(pageSize)
            .observeOn(schedulerProvider.ui())
            .subscribe(::onCharactersReload, ::onError)
            .addTo(disposables)
    }

    private fun onCharactersReceived(pagedList: PagedList<MarvelCharacter>) {
        statePublisher.onNext(initialState.copy(loading = false, characters = pagedList))
    }

    private fun onCharactersReload(pagedList: PagedList<MarvelCharacter>) {
        statePublisher.onNext(initialState.copy(loading = false, reloaded = true, characters = pagedList))
    }

    private fun onError(throwable: Throwable) {
        statePublisher.onNext(
            initialState.copy(
                loading = false,
                hasError = true,
                errorMessage = throwable.message
            )
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}