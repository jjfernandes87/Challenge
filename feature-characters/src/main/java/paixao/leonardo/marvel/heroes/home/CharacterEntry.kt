package paixao.leonardo.marvel.heroes.home

import android.view.View
import com.xwray.groupie.viewbinding.BindableItem
import paixao.leonardo.marvel.heroes.home.databinding.ItemCharacterBinding

class CharacterEntry : BindableItem<ItemCharacterBinding>() {
    override fun bind(viewBinding: ItemCharacterBinding, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getLayout(): Int = R.layout.item_character

    override fun initializeViewBinding(view: View): ItemCharacterBinding =
        ItemCharacterBinding.bind(view)

}