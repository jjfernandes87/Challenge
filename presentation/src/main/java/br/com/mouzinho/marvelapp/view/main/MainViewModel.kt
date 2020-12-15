package br.com.mouzinho.marvelapp.view.main

import androidx.appcompat.widget.Toolbar
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class MainViewModel @ViewModelInject constructor() : ViewModel() {
    val stateObservable: Observable<MainViewState> by lazy { statePublisher.hide() }
    val actionsObservable: Observable<MainViewActions> by lazy { actionsPublisher.hide() }

    private val statePublisher by lazy { PublishSubject.create<MainViewState>() }
    private val actionsPublisher by lazy { PublishSubject.create<MainViewActions>() }

    fun search(name: String) {
        statePublisher.onNext(MainViewState.Search(name))
    }

    fun reloadCharacters() {
        statePublisher.onNext(MainViewState.Reload)
    }

    fun updateToolbarTitle(title: String) {
        actionsPublisher.onNext(MainViewActions.ChangeToolbarTitle(title))
    }

    fun setupToolbar(toolbar: Toolbar) {
        actionsPublisher.onNext(MainViewActions.SetupToolbar(toolbar))
    }
}