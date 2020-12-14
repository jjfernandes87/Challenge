package br.com.mouzinho.marvelapp.view.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import io.reactivex.subjects.PublishSubject

class MainViewModel @ViewModelInject constructor() : ViewModel() {
    val stateObservable by lazy { statePublisher.hide() }

    private val statePublisher by lazy { PublishSubject.create<MainViewState>() }

    fun search(name: String) {
        statePublisher.onNext(MainViewState.Search(name))
    }

    fun reloadCharacters() {
        statePublisher.onNext(MainViewState.Reload)
    }
}