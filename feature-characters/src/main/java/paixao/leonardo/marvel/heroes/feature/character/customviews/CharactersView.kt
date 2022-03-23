package paixao.leonardo.marvel.heroes.feature.character.customviews

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import org.kodein.di.DIAware
import org.kodein.di.android.closestDI
import paixao.leonardo.marvel.heroes.domain.models.MarvelCharacter
import paixao.leonardo.marvel.heroes.feature.R
import paixao.leonardo.marvel.heroes.feature.character.CharacterViewModel
import paixao.leonardo.marvel.heroes.feature.character.entries.CharacterItemEntry
import paixao.leonardo.marvel.heroes.feature.core.stateMachine.StateMachineEvent
import paixao.leonardo.marvel.heroes.feature.core.utils.ktx.collectIn
import paixao.leonardo.marvel.heroes.feature.core.utils.lifecycleScope
import paixao.leonardo.marvel.heroes.feature.core.utils.viewModel
import paixao.leonardo.marvel.heroes.feature.databinding.ItemCharacterListBinding

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

    override fun onFinishInflate() {
        super.onFinishInflate()
        initializeAdapter()
        retrieveCharacterList()
    }

    private fun retrieveCharacterList() {
        viewModel.retrieveCharacters().collectIn(lifecycleScope) { event ->
            when (event) {
                is StateMachineEvent.Start -> println(event.toString())
                is StateMachineEvent.Success -> populateCharacterRv(event.value)
                is StateMachineEvent.Failure -> println(event.toString())
                is StateMachineEvent.Finish -> println(event.toString())
            }
        }
    }

    private fun populateCharacterRv(characters: List<MarvelCharacter>) {
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
        val isFavorite = viewModel.retrieveRealFavoriteStatus(character)
        if (isFavorite) {
            viewModel.removeFavoriteCharacter(character = character)
        } else {
            viewModel.saveFavoriteCharacter(character = character)
        }
            .collectIn(lifecycleScope) { event ->
                when (event) {
                    is StateMachineEvent.Start -> println(event.toString())
                    is StateMachineEvent.Success -> handleSuccessSaving(
                        character,
                        imageView,
                        event.value
                    )
                    is StateMachineEvent.Failure -> println(event.toString())
                    is StateMachineEvent.Finish -> println(event.toString())
                }
            }
    }

    private fun handleSuccessSaving(
        character: MarvelCharacter,
        imageView: AppCompatImageView,
        isSaved: Boolean
    ) {
        val imsRes = if (isSaved) R.drawable.ic_filled_star
        else R.drawable.ic_star
        println("RESULT ------------> $imsRes ------- $isSaved")
        val drawable = context.getDrawable(imsRes)
        imageView.setImageDrawable(drawable)

        //viewModel.updateFavoriteCharactersComponent(character)

    }

    private companion object {
        private const val ADAPTER_COLUMNS = 2
    }

}
