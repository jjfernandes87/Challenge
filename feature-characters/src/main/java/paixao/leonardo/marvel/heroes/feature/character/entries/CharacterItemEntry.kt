package paixao.leonardo.marvel.heroes.feature.character.entries

import android.view.View
import com.bumptech.glide.Glide
import com.xwray.groupie.viewbinding.BindableItem
import paixao.leonardo.marvel.heroes.domain.models.Character
import paixao.leonardo.marvel.heroes.feature.R
import paixao.leonardo.marvel.heroes.feature.databinding.ItemCharacterBinding

class CharacterItemEntry(private val character: Character) : BindableItem<ItemCharacterBinding>() {
    override fun bind(viewBinding: ItemCharacterBinding, position: Int) {
        viewBinding.apply {
            characterName.text = character.name

            Glide.with(viewBinding.root.context)
                .load(character.imageUrl)
                .into(viewBinding.imageView)
        }
    }

    override fun getLayout(): Int = R.layout.item_character

    override fun initializeViewBinding(view: View): ItemCharacterBinding =
        ItemCharacterBinding.bind(view)

}