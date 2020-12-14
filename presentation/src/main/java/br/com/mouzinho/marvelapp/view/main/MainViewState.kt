package br.com.mouzinho.marvelapp.view.main

sealed class MainViewState {
    data class Search(val text: String) : MainViewState()
}