package paixao.leonardo.marvel.heroes.feature.character.screens.listing.entries

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import com.xwray.groupie.viewbinding.BindableItem
import paixao.leonardo.marvel.heroes.domain.models.MarvelCharacter
import paixao.leonardo.marvel.heroes.feature.R
import paixao.leonardo.marvel.heroes.feature.core.exceptions.MarvelException
import paixao.leonardo.marvel.heroes.feature.databinding.ItemCharacterListBinding

class CharacterListEntry(
    private val handleError: (MarvelException, () -> Unit) -> Unit = { _, _ -> },
    private val handleLoading: (Boolean) -> Unit,
    private val navigateToDetails: (MarvelCharacter, AppCompatImageView) -> Unit,
) : BindableItem<ItemCharacterListBinding>() {
    override fun bind(viewBinding: ItemCharacterListBinding, position: Int) {
        viewBinding.root.handleError = handleError
        viewBinding.root.handleLoading = handleLoading
        viewBinding.root.navigateToDetails = navigateToDetails
    }

    override fun getLayout(): Int = R.layout.item_character_list

    override fun initializeViewBinding(view: View): ItemCharacterListBinding =
        ItemCharacterListBinding.bind(view)

}