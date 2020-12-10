package br.com.mouzinho.marvelapp.view.main

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

class MainViewModel @ViewModelInject constructor(
    private val getCharacters: GetCharacters,
    private val schedulerProvider: SchedulerProvider
) : ViewModel() {
    val stateObservable: Observable<MainState> by lazy { statePublisher.hide() }

    private val statePublisher by lazy { PublishSubject.create<MainState>() }
    private val initialState = MainState()
    private val disposables = CompositeDisposable()

    fun loadCharacters(pageSize: Int = 20) {
        getCharacters(pageSize)
            .observeOn(schedulerProvider.ui())
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