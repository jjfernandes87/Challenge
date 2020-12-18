package br.com.mouzinho.marvelapp.view.characters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.mouzinho.domain.entity.character.MarvelCharacter
import br.com.mouzinho.marvelapp.R
import br.com.mouzinho.marvelapp.databinding.ViewHolderCharacterBinding
import com.bumptech.glide.Glide

class CharacterViewHolder private constructor(
    private val binding: ViewHolderCharacterBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        character: MarvelCharacter?,
        onFavoriteClickListener: (MarvelCharacter) -> Unit,
        onCharacterClickListener: (MarvelCharacter) -> Unit
    ) {
        with(binding) {
            this.character = character
            this.onFavoriteClickListener = View.OnClickListener { character?.let { onFavoriteClickListener(character) } }
            this.onRootClickListener = View.OnClickListener { character?.let { onCharacterClickListener(character) } }
            character?.thumbnail?.let { thumbnail ->
                Glide.with(root.context.applicationContext)
                    .load(thumbnail.landscapeMediumUrl)
                    .placeholder(R.drawable.thumb_placeholder)
                    .into(binding.imageViewAvatar)
            }
        }
    }

    companion object {
        fun inflate(parent: ViewGroup) = CharacterViewHolder(
            ViewHolderCharacterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}