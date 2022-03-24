package paixao.leonardo.marvel.heroes.feature.character.screens.listing

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.characters_activity.view.*
import org.kodein.di.DI
import org.kodein.di.DIAware
import paixao.leonardo.marvel.heroes.feature.character.screens.listing.entries.CharacterListEntry
import paixao.leonardo.marvel.heroes.feature.character.screens.listing.entries.FavoriteCharactersEntry
import paixao.leonardo.marvel.heroes.feature.core.exceptions.MarvelException
import paixao.leonardo.marvel.heroes.feature.core.utils.OnTabSelectedListener
import paixao.leonardo.marvel.heroes.feature.core.utils.isVisible
import paixao.leonardo.marvel.heroes.feature.core.utils.selfInject
import paixao.leonardo.marvel.heroes.feature.databinding.CharactersActivityBinding

class CharactersActivity : AppCompatActivity(), DIAware {

    override val di: DI = selfInject()

    private lateinit var binding: CharactersActivityBinding

    private val tabAdapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    private val viewPagerCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            binding.tabLayout.getTabAt(position)?.select()
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
        binding.viewPager.registerOnPageChangeCallback(this.viewPagerCallback)

        binding.tabLayout.addOnTabSelectedListener(
            OnTabSelectedListener { position ->
                binding.viewPager.currentItem = position
            }
        )
    }

    private fun handleLoading(isLoading: Boolean) {
        binding.errorView.root.isVisible = false
        binding.viewPager.isVisible = !isLoading
        binding.loadingView.isVisible = isLoading
    }

    private fun handleError(error: MarvelException, onRetry: () -> Unit) {
        binding.viewPager.viewPager.isVisible = false
        binding.loadingView.isVisible = false
        (binding.errorView.root).apply {
            visibility = View.VISIBLE
            renderError(
                marvelException = error,
                backAction = { handleLoading(false) },
                retryAction = onRetry
            )
        }
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