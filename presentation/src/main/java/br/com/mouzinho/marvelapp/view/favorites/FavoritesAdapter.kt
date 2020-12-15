package br.com.mouzinho.marvelapp.view.favorites

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import br.com.mouzinho.domain.entity.favorite.FavoriteCharacter

class FavoritesAdapter(
    private val onRemoveFromFavoriteClick: (FavoriteCharacter) -> Unit,
    private val onRootClick: (FavoriteCharacter) -> Unit
) : ListAdapter<FavoriteCharacter, FavoriteViewHolder>(Diff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder.inflate(parent)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(getItem(position), onRemoveFromFavoriteClick, onRootClick)
    }

    companion object Diff : DiffUtil.ItemCallback<FavoriteCharacter>() {
        override fun areItemsTheSame(oldItem: FavoriteCharacter, newItem: FavoriteCharacter): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FavoriteCharacter, newItem: FavoriteCharacter): Boolean {
            return oldItem == newItem
        }
    }
}

