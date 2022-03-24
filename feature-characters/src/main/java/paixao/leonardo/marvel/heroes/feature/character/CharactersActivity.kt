package paixao.leonardo.marvel.heroes.feature.character

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.characters_activity.view.*
import org.kodein.di.DI
import org.kodein.di.DIAware
import paixao.leonardo.marvel.heroes.feature.character.entries.CharacterListEntry
import paixao.leonardo.marvel.heroes.feature.character.entries.FavoriteCharactersEntry
import paixao.leonardo.marvel.heroes.feature.core.exceptions.MarvelException
import paixao.leonardo.marvel.heroes.feature.core.utils.OnTabSelectedListener
import paixao.leonardo.marvel.heroes.feature.core.utils.isInvisble
import paixao.leonardo.marvel.heroes.feature.core.utils.isVisible
import paixao.leonardo.marvel.heroes.feature.core.utils.selfInject
import paixao.leonardo.marvel.heroes.feature.databinding.CharactersActivityBinding

class CharactersActivity : AppCompatActivity(), DIAware {

    override val di: DI = selfInject()

    private lateinit var binding: CharactersActivityBinding

    private val tabAdapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    private val pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            binding.tabLayout.setScrollPosition(position, 0F, true)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CharactersActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewPager()
        initializeViews()
    }

    private fun setupViewPager() {
        binding.viewPager.adapter = tabAdapter
        binding.viewPager.registerOnPageChangeCallback(pageChangeCallback)

        binding.tabLayout.addOnTabSelectedListener(
            OnTabSelectedListener { position ->
                binding.viewPager.currentItem = position
            }
        )
    }

    private fun handleLoading(isLoading: Boolean) {
        binding.errorView.root.isVisible = false
        binding.tabLayout.isInvisble = true
        binding.loadingView.isVisible = isLoading
    }

    private fun handleError(error: MarvelException, onRetry: () -> Unit) {
        binding.viewPager.viewPager.isVisible = false
        binding.tabLayout.isVisible = false
        binding.loadingView.isVisible = false
        (binding.errorView.root).apply {
            visibility = View.VISIBLE
            renderError(
                marvelException = error,
                backAction = { handleLoading(false) },
                retryAction = onRetry
            )
        }
        binding.errorView.root.bringToFront()
    }

    private fun initializeViews() {
        tabAdapter.add(
            CharacterListEntry(
                handleError = { marvelException, onRetry -> handleError(marvelException, onRetry) },
                handleLoading = ::handleLoading
            )
        )
        tabAdapter.add(
            FavoriteCharactersEntry(
                handleError = { marvelException, onRetry -> handleError(marvelException, onRetry) },
                handleLoading = ::handleLoading
            )
        )
    }
}