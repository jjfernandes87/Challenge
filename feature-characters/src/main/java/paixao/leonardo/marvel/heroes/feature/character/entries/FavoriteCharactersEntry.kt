package paixao.leonardo.marvel.heroes.feature.character.entries

import android.view.View
import com.xwray.groupie.viewbinding.BindableItem
import paixao.leonardo.marvel.heroes.feature.R
import paixao.leonardo.marvel.heroes.feature.databinding.ItemFavoriteCharacterListBinding

class FavoriteCharactersEntry : BindableItem<ItemFavoriteCharacterListBinding>() {
    override fun bind(viewBinding: ItemFavoriteCharacterListBinding, position: Int) {}

    override fun getLayout(): Int = R.layout.item_favorite_character_list

    override fun initializeViewBinding(view: View): ItemFavoriteCharacterListBinding =
        ItemFavoriteCharacterListBinding.bind(view)

}