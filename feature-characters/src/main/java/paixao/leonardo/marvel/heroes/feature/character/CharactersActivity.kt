package paixao.leonardo.marvel.heroes.feature.character

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import org.kodein.di.DI
import org.kodein.di.DIAware
import paixao.leonardo.marvel.heroes.domain.models.Character
import paixao.leonardo.marvel.heroes.feature.core.ktx.collectIn
import paixao.leonardo.marvel.heroes.feature.core.stateMachine.StateMachineEvent
import paixao.leonardo.marvel.heroes.feature.core.utils.selfInject
import paixao.leonardo.marvel.heroes.feature.core.utils.viewModel
import paixao.leonardo.marvel.heroes.feature.databinding.CharactersActivityBinding

class CharactersActivity : AppCompatActivity(), DIAware {

    override val di: DI = selfInject()

    private lateinit var viewBinding: CharactersActivityBinding

    private val viewModel by viewModel<CharacterViewModel>()

    private val adapter by lazy { GroupAdapter<GroupieViewHolder>() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = CharactersActivityBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        initializeAdapter()
        viewBinding.buttonRequest.setOnClickListener {
            viewModel.retrieveCharacters().collectIn(lifecycleScope) { event ->
                when (event) {
                    is StateMachineEvent.Start -> println(event.toString())
                    is StateMachineEvent.Success -> populateCharacterRv(event.value)
                    is StateMachineEvent.Failure -> println(event.toString())
                    is StateMachineEvent.Finish -> println(event.toString())
                }
            }
        }

    }

    private fun initializeAdapter() {
        adapter.clear()
        viewBinding.charactersRv.adapter = adapter
    }

    private fun populateCharacterRv(characters: List<Character>) {
        val items = characters.map(::CharacterEntry)
        adapter.addAll(items)
    }
}