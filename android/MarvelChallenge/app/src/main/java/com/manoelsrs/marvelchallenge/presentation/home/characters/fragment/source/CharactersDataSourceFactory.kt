package com.manoelsrs.marvelchallenge.presentation.home.characters.fragment.source

import androidx.paging.DataSource
import com.manoelsrs.marvelchallenge.core.common.BasePresenter
import com.manoelsrs.marvelchallenge.model.Character
import com.manoelsrs.marvelchallenge.presentation.home.characters.actions.GetCharacters

class CharactersDataSourceFactory(
    private val presenter: BasePresenter,
    private val getCharacters: GetCharacters
) : DataSource.Factory<Int, Character>() {

    override fun create(): DataSource<Int, Character> =
        CharactersDataSource(presenter, getCharacters)
}