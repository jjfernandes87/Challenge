package paixao.leonardo.marvel.heroes.feature.character.screens.listing.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.doOnPreDraw
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.coroutines.flow.consumeAsFlow
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
import paixao.leonardo.marvel.heroes.feature.core.views.errorView.EndlessRecyclerViewScrollListener
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

    private val endlessScroll by lazy {
        EndlessRecyclerViewScrollListener(
            binding.charactersRv.layoutManager as LinearLayoutManager
        ) { retrieveCharacters(isRefreshing = false) }
    }

    private val gridAdapter by lazy {
        GroupAdapter<GroupieViewHolder>().apply {
            spanCount = ADAPTER_COLUMNS
        }
    }

    var handleLoading: (Boolean) -> Unit = {}
    var handleError: (MarvelException, OnRetry) -> Unit = { _, _ -> }
    var navigateToDetails: (MarvelCharacter, AppCompatImageView) -> Unit = { _, _ -> }

    override fun onFinishInflate() {
        super.onFinishInflate()
        initializeAdapter()
        retrieveCharacters(true)
        handleFavoriteDataChange()
    }

    private fun resetState() {
        gridAdapter.clear()
        endlessScroll.resetState()
    }

    private fun turnOnPagingEvents() {
        binding.charactersRv.addOnScrollListener(endlessScroll)
        doOnPreDraw { endlessScroll.locked = false }
    }

    private fun turnOffPagingEvents() {
        endlessScroll.locked = true
        binding.charactersRv.removeOnScrollListener(endlessScroll)
    }

    private fun handleFavoriteDataChange() {
        viewModel.listenFavoriteCharactersChange().collectIn(lifecycleScope) {
            val currentItem = try { gridAdapter.getGroupAtAdapterPosition(it.position)
            } catch (error: Throwable){
                null
            }

            if(currentItem != null){
                val updateItem = retrieveCharacterEntry(it)
                gridAdapter.removeGroupAtAdapterPosition(it.position)
                gridAdapter.add(it.position, updateItem)
            }

        }
    }

    private fun retrieveCharacters(isRefreshing: Boolean = false) {
        turnOffPagingEvents()
        viewModel.retrieveCharacters(isRefreshing).collectIn(lifecycleScope) { event ->
            when (event) {
                is StateMachineEvent.Start -> binding.refreshLayout.isRefreshing = true
                is StateMachineEvent.Success -> populateCharacterRv(event.value)
                is StateMachineEvent.Failure -> handleError(event)
                else -> Unit
            }
            turnOnPagingEvents()
        }
    }

    private fun handleError(event: StateMachineEvent.Failure) {
        binding.refreshLayout.isRefreshing = false
        handleError(
            event.exception,
            ::retrieveCharacters
        )
    }

    private fun populateCharacterRv(characters: List<MarvelCharacter>) {
        binding.refreshLayout.isRefreshing = false
        if (characters.isEmpty()) {
            turnOffPagingEvents()
            return
        }

        val items = characters.map { character ->
            retrieveCharacterEntry(character)
        }
        gridAdapter.addAll(items)

    }

    private fun retrieveCharacterEntry(character: MarvelCharacter) =
        CharacterItemEntry(
            character = character,
            favoriteAction = { clickedCharacter, imageView ->
                saveOrRemoveFavoriteCharacter(
                    clickedCharacter,
                    imageView
                )
            },
            navigateToDetailsAction = navigateToDetails
        )

    private fun initializeAdapter() {
        binding.charactersRv.apply {
            layoutManager = GridLayoutManager(context, gridAdapter.spanCount)
            adapter = gridAdapter
        }

        binding.refreshLayout.setOnRefreshListener {
            retrieveCharacters(true)
            resetState()
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

    private fun showErrorToast() {
        binding.refreshLayout.isRefreshing = false
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
