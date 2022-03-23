package paixao.leonardo.marvel.heroes.feature.character.entries

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.res.ResourcesCompat
import com.bumptech.glide.Glide
import com.xwray.groupie.viewbinding.BindableItem
import paixao.leonardo.marvel.heroes.domain.models.MarvelCharacter
import paixao.leonardo.marvel.heroes.feature.R
import paixao.leonardo.marvel.heroes.feature.databinding.ItemCharacterBinding

class CharacterItemEntry(
    private val character: MarvelCharacter,
    private val action: (MarvelCharacter, AppCompatImageView) -> Unit = { _, _ -> }
) : BindableItem<ItemCharacterBinding>() {
    override fun bind(viewBinding: ItemCharacterBinding, position: Int) {
        viewBinding.apply {
            characterName.text = character.name

            Glide.with(viewBinding.root.context)
                .load(character.imageUrl)
                .into(viewBinding.imageView)

            viewBinding.starImg.apply {
                val starImgDrawable = context.retrieveStarImgDrawable(character)

                starImgDrawable?.let(::setImageDrawable)

                setOnClickListener {
                    action(character, viewBinding.starImg)
                }
            }

        }
    }

    private fun Context.retrieveStarImgDrawable(character: MarvelCharacter): Drawable? {
        val imgRes = if (character.isFavorite)
            R.drawable.ic_filled_star
        else R.drawable.ic_star

        return ResourcesCompat.getDrawable(resources, imgRes, theme)
    }

    override fun getLayout(): Int = R.layout.item_character

    override fun initializeViewBinding(view: View): ItemCharacterBinding =
        ItemCharacterBinding.bind(view)

}