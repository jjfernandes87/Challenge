package br.com.mouzinho.marvelapp.view.characterDetails

import br.com.mouzinho.domain.entity.character.Series
import br.com.mouzinho.domain.entity.comic.ComicDetails

sealed class CharacterDetailsViewState {
    data class ToggleLoading(val isLoading: Boolean) : CharacterDetailsViewState()

    data class ShowDetails(
        val comics: List<ComicDetails>,
        val series: List<Series>, //TODO SerieDetails
        val headerImageUrl: String,
        val description: String
    ) : CharacterDetailsViewState()
}