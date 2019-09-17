package com.manoelsrs.marvelchallenge.presentation.home.characters.fragment

import com.manoelsrs.marvelchallenge.model.Character

interface CharactersFragmentContract {
    fun updateCharacters(characters: List<Character>)
}