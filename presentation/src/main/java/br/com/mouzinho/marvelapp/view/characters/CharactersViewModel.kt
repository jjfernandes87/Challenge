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
            .subscribe(::onCharacterResultReceived)
            .addTo(disposables)
    }

    private fun onCharacterResultReceived(pagedList: PagedList<MarvelCharacter>) {
        statePublisher.onNext(initialState.copy(loading = false, characters = pagedList))
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}