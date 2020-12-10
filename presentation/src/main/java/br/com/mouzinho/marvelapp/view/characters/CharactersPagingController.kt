package br.com.mouzinho.marvelapp.view.characters

import android.view.View
import br.com.mouzinho.domain.entity.character.MarvelCharacter
import br.com.mouzinho.marvelapp.CharacterBindingModel_
import br.com.mouzinho.marvelapp.LoadingBindingModel_
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharactersPagingController @Inject constructor(
    private val onViewClick: (MarvelCharacter) -> Unit,
    private val onFavoriteClick: (MarvelCharacter) -> Unit,
) : PagedListEpoxyController<MarvelCharacter>() {
    var isLoading = false
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
                .onFavoriteClickListener(View.OnClickListener { onFavoriteClick(item) })
                .onViewClickListener(View.OnClickListener { onViewClick(item) })
        }
    }

    override fun addModels(models: List<EpoxyModel<*>>) {
        if (isLoading) {
            super.addModels(
                models
                    .plus(LoadingBindingModel_().id(LOADING_ID).spanSizeOverride { _, _, _ -> 2 })
                    .distinct()
            )
        } else
            super.addModels(models.distinct())
    }

    companion object {
        private const val LOADING_ID = "loading"
    }
}