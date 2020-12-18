package br.com.mouzinho.marvelapp.view.characterDetails

import br.com.mouzinho.domain.entity.comic.MediaDetails

sealed class CharacterDetailsViewState {
    data class ToggleLoading(val isLoading: Boolean) : CharacterDetailsViewState()

    data class ToggleFavorite(val isFavorite: Boolean) : CharacterDetailsViewState()

    data class ShowDetails(
        val comics: List<MediaDetails>,
        val series: List<MediaDetails>,
        val headerImageUrl: String,
        val description: String
    ) : CharacterDetailsViewState()
}