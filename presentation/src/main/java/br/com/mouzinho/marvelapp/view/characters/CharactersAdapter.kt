package br.com.mouzinho.marvelapp.view.characters

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import br.com.mouzinho.domain.entity.character.MarvelCharacter

class CharactersAdapter(
    private val onFavoriteClickListener: (MarvelCharacter) -> Unit,
    private val onCharacterClickListener: (MarvelCharacter) -> Unit
) : PagedListAdapter<MarvelCharacter, CharacterViewHolder>(Diff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder.inflate(parent)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(getItem(position), onFavoriteClickListener, onCharacterClickListener)
    }

    companion object Diff : DiffUtil.ItemCallback<MarvelCharacter>() {
        override fun areItemsTheSame(oldItem: MarvelCharacter, newItem: MarvelCharacter): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MarvelCharacter, newItem: MarvelCharacter): Boolean {
            return oldItem == newItem
        }
    }

    fun updateFavorite(character: MarvelCharacter) {
        val characterOnList = currentList?.find { it.id == character.id }
        val index = characterOnList?.let { currentList?.indexOf(it) }
        characterOnList?.isFavorite = character.isFavorite
        index?.let { notifyItemChanged(it) }
    }
}

