package br.com.mouzinho.marvelapp.view.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import br.com.mouzinho.domain.interactor.character.SearchCharacter

class MainViewModel @ViewModelInject constructor(
    private val searchCharacter: SearchCharacter
) : ViewModel() {

    fun search(name: String) {
        searchCharacter(name)
    }
}