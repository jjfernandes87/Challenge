package paixao.leonardo.marvel.heroes.feature.character.entries

import android.view.View
import com.xwray.groupie.viewbinding.BindableItem
import paixao.leonardo.marvel.heroes.feature.R
import paixao.leonardo.marvel.heroes.feature.databinding.ItemCharacterListBinding

class CharacterListEntry : BindableItem<ItemCharacterListBinding>() {
    override fun bind(viewBinding: ItemCharacterListBinding, position: Int) {}

    override fun getLayout(): Int = R.layout.item_character_list

    override fun initializeViewBinding(view: View): ItemCharacterListBinding =
        ItemCharacterListBinding.bind(view)

}