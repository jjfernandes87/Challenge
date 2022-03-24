package paixao.leonardo.marvel.heroes.feature.character.screens.listing.customviews

import android.content.Context
import android.util.AttributeSet
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import org.kodein.di.DIAware
import org.kodein.di.android.closestDI
import paixao.leonardo.marvel.heroes.domain.models.MarvelCharacter
import paixao.leonardo.marvel.heroes.feature.R
import paixao.leonardo.marvel.heroes.feature.character.screens.listing.CharacterViewModel
import paixao.leonardo.marvel.heroes.feature.character.screens.listing.entries.CharacterItemEntry
import paixao.leonardo.marvel.heroes.feature.core.exceptions.MarvelException
import paixao.leonardo.marvel.heroes.feature.core.stateMachine.StateMachineEvent
import paixao.leonardo.marvel.heroes.feature.core.utils.ktx.collectIn
import paixao.leonardo.marvel.heroes.feature.core.utils.lifecycleScope
import paixao.leonardo.marvel.heroes.feature.core.utils.viewModel
import paixao.leonardo.marvel.heroes.feature.databinding.ItemCharacterListBinding

typealias OnRetry = () -> Unit

class CharacterView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), DIAware {

    override val di by closestDI()

    private val viewModel by viewModel<CharacterViewModel>()

    private val binding
        get() = ItemCharacterListBinding.bind(this)


    private val gridAdapter by lazy {
        GroupAdapter<GroupieViewHolder>().apply {
            spanCount = ADAPTER_COLUMNS
        }
    }

    var handleError: (MarvelException, OnRetry) -> Unit = { _, _ -> }
    var handleLoading: (Boolean) -> Unit = {}

    override fun onFinishInflate() {
        super.onFinishInflate()
        initializeAdapter()
        retrieveCharacterList()
    }

    private fun retrieveCharacterList() {
        viewModel.retrieveCharacters().collectIn(lifecycleScope) { event ->
            when (event) {
                is StateMachineEvent.Start -> handleLoading(true)
                is StateMachineEvent.Success -> populateCharacterRv(event.value)
                is StateMachineEvent.Failure -> handleError(
                    event.exception,
                    ::retrieveCharacterList
                )
                else -> Unit
            }
        }
    }

    private fun populateCharacterRv(characters: List<MarvelCharacter>) {
        handleLoading(false)
        val items = characters.map { character ->
            CharacterItemEntry(
                character = character,
                action = { clickedCharacter, imageView ->
                    saveOrRemoveFavoriteCharacter(
                        clickedCharacter,
                        imageView
                    )
                }
            )
        }
        gridAdapter.addAll(items)
    }

    private fun initializeAdapter() {
        binding.charactersRv.apply {
            layoutManager = GridLayoutManager(context, gridAdapter.spanCount)
            adapter = gridAdapter
        }
    }

    private fun saveOrRemoveFavoriteCharacter(
        character: MarvelCharacter,
        imageView: AppCompatImageView
    ) {
        viewModel.saveOrRemoveFavoriteCharacter(character).collectIn(lifecycleScope) { event ->
            when (event) {
                is StateMachineEvent.Success -> handleSuccessSaving(
                    imageView,
                    event.value
                )
                is StateMachineEvent.Failure -> showErrorToast()
                else -> Unit
            }
        }
    }

    private fun showErrorToast(){
        Toast.makeText(context, R.string.error_saving_favorites, Toast.LENGTH_LONG).show()
    }

    private fun handleSuccessSaving(
        imageView: AppCompatImageView,
        isSaved: Boolean
    ) {
        handleLoading(false)
        val imsRes = if (isSaved)
            R.drawable.ic_filled_star
        else
            R.drawable.ic_star

        AppCompatResources.getDrawable(context, imsRes).let(imageView::setImageDrawable)
    }

    private companion object {
        private const val ADAPTER_COLUMNS = 2
    }

}
