package br.com.mouzinho.marvelapp.view.main

import br.com.mouzinho.domain.entity.character.Character

data class MainState(
    val loading: Boolean = true,
    val characters: List<Character> = emptyList(),
    val hasError: Boolean = false,
    val errorMessage: String? = null
)