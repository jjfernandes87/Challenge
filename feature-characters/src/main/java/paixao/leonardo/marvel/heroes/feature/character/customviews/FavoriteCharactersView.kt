package paixao.leonardo.marvel.heroes.feature.character.customviews

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import org.kodein.di.DIAware
import org.kodein.di.android.closestDI
import paixao.leonardo.marvel.heroes.domain.models.Character
import paixao.leonardo.marvel.heroes.feature.character.CharacterViewModel
import paixao.leonardo.marvel.heroes.feature.character.entries.CharacterItemEntry
import paixao.leonardo.marvel.heroes.feature.core.stateMachine.StateMachineEvent
import paixao.leonardo.marvel.heroes.feature.core.utils.ktx.collectIn
import paixao.leonardo.marvel.heroes.feature.core.utils.lifecycleScope
import paixao.leonardo.marvel.heroes.feature.core.utils.viewModel
import paixao.leonardo.marvel.heroes.feature.databinding.ItemFavoriteCharacterListBinding

class FavoriteCharactersView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), DIAware {
    override val di by closestDI()

    private val viewModel by viewModel<CharacterViewModel>()

    private val binding
        get() = ItemFavoriteCharacterListBinding.bind(this)

    private val gridAdapter by lazy {
        GroupAdapter<GroupieViewHolder>().apply {
            spanCount = ADAPTER_COLUMNS
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        initializeAdapter()
        retrieveFavoriteCharacters()
    }

    private fun retrieveFavoriteCharacters() {
        viewModel.retrieveFavoriteCharacter().collectIn(lifecycleScope) { event ->
            when (event) {
                is StateMachineEvent.Start -> println(event.toString())
                is StateMachineEvent.Success -> populateCharacterRv(event.value)
                is StateMachineEvent.Failure -> println(event.toString())
                is StateMachineEvent.Finish -> println(event.toString())
            }
        }
    }

    private fun populateCharacterRv(characters: List<Character>) {
        val items = characters.map(::CharacterItemEntry)
        gridAdapter.addAll(items)
    }

    private fun initializeAdapter() {
        binding.charactersRv.apply {
            layoutManager = GridLayoutManager(context, gridAdapter.spanCount)
            adapter = gridAdapter
        }
    }

    private companion object {
        private const val ADAPTER_COLUMNS = 2
    }

}
