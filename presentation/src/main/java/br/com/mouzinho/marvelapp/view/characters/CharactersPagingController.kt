package br.com.mouzinho.marvelapp.view.characters

import android.view.View
import br.com.mouzinho.domain.entity.character.MarvelCharacter
import br.com.mouzinho.domain.entity.character.Thumbnail
import br.com.mouzinho.marvelapp.CharacterBindingModel_
import br.com.mouzinho.marvelapp.ErrorBindingModel_
import br.com.mouzinho.marvelapp.LoadingBindingModel_
import br.com.mouzinho.marvelapp.R
import br.com.mouzinho.marvelapp.databinding.ViewHolderCharacterBinding
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController
import com.bumptech.glide.Glide
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharactersPagingController @Inject constructor(
    private val onViewClick: (MarvelCharacter) -> Unit,
    private val onFavoriteClick: (MarvelCharacter) -> Unit,
) : PagedListEpoxyController<MarvelCharacter>() {
    var loadStatus: LoadMarvelCharactersStatus = LoadMarvelCharactersStatus.LOAD
        set(value) {
            field = value
            requestModelBuild()
        }

    init {
        isDebugLoggingEnabled = true
    }

    override fun buildItemModel(currentPosition: Int, item: MarvelCharacter?): EpoxyModel<*> {
        return if (item == null) {
            LoadingBindingModel_()
                .id(LOADING_ID)
                .spanSizeOverride { _, _, _ -> 2 }
        } else {
            CharacterBindingModel_()
                .id(item.id)
                .character(item)
                .onBind { model, view, _ ->
                    (view.dataBinding as? ViewHolderCharacterBinding)?.let { binding ->
                        val thumbnail = model.character().thumbnail
                        Glide.with(binding.root.context.applicationContext)
                            .load("${thumbnail?.path}/${Thumbnail.SIZE_LANDSCAPE_MEDIUM}.${thumbnail?.extension}")
                            .placeholder(R.drawable.thumb_placeholder)
                            .into(binding.imageViewAvatar)
                    }
                }
                .onFavoriteClickListener(View.OnClickListener { onFavoriteClick(item) })
                .onViewClickListener(View.OnClickListener { onViewClick(item) })
        }
    }

    override fun addModels(models: List<EpoxyModel<*>>) {
        when (loadStatus) {
            LoadMarvelCharactersStatus.LOAD -> super.addModels(
                models
                    .plus(
                        LoadingBindingModel_()
                            .id(LOADING_ID)
                            .spanSizeOverride { _, _, _ -> 2 }
                    )
                    .distinct()
            )
            LoadMarvelCharactersStatus.ERROR -> super.addModels(
                models
                    .plus(
                        ErrorBindingModel_()
                            .id(LOADING_ID)
                            .spanSizeOverride { _, _, _ -> 2 }
                    )
                    .distinct()
            )
            else -> super.addModels(models.distinct())
        }
    }

    companion object {
        private const val LOADING_ID = "loading"
    }
}