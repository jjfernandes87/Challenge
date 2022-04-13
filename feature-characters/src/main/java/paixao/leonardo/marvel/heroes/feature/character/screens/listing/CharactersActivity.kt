package paixao.leonardo.marvel.heroes.feature.character.screens.listing

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.characters_activity.view.*
import kotlinx.coroutines.launch
import org.kodein.di.DI
import org.kodein.di.DIAware
import paixao.leonardo.marvel.heroes.domain.models.MarvelCharacter
import paixao.leonardo.marvel.heroes.feature.R
import paixao.leonardo.marvel.heroes.feature.character.screens.NavigationParams.CHARACTER_DETAILS_PARAMS
import paixao.leonardo.marvel.heroes.feature.character.screens.details.CharacterDetailsActivity
import paixao.leonardo.marvel.heroes.feature.character.screens.listing.entries.CharacterListEntry
import paixao.leonardo.marvel.heroes.feature.character.screens.listing.entries.FavoriteCharactersEntry
import paixao.leonardo.marvel.heroes.feature.core.exceptions.MarvelException
import paixao.leonardo.marvel.heroes.feature.core.utils.OnTabSelectedListener
import paixao.leonardo.marvel.heroes.feature.core.utils.isVisible
import paixao.leonardo.marvel.heroes.feature.core.utils.putParams
import paixao.leonardo.marvel.heroes.feature.core.utils.selfInject
import paixao.leonardo.marvel.heroes.feature.core.utils.viewModel
import paixao.leonardo.marvel.heroes.feature.databinding.CharactersActivityBinding


class CharactersActivity : AppCompatActivity(), DIAware {

    override val di: DI = selfInject()

    private lateinit var binding: CharactersActivityBinding

    private val viewModel by viewModel<CharacterViewModel>()

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


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            val character = data?.getSerializableExtra(CHARACTER_DETAILS_PARAMS) as? MarvelCharacter
            val lastCharacterDataAndImgView = viewModel.lastCharacterStatusBeforeNavigateToDetails()

            if (character != null && lastCharacterDataAndImgView != null) {
                val (lastCharacterData, imageView) = lastCharacterDataAndImgView
                if (lastCharacterData.isFavorite != character.isFavorite) {
                    updateStarViewOfListView(
                        imageView = imageView,
                        isSaved = character.isFavorite
                    )
                    viewModel.storeLastUpdatedCharacter(character = character)
                    notifyFavoriteListViewDataChanged(marvelCharacter = character)
                }
            }
        }
    }

    private fun notifyFavoriteListViewDataChanged(marvelCharacter: MarvelCharacter) {
        lifecycleScope.launch {
            viewModel.notifyFavoriteListViewDataChanged(marvelCharacter)
        }
    }

    private fun initializeViews() {
        tabAdapter.add(
            CharacterListEntry(
                handleError = { marvelException, onRetry -> handleError(marvelException, onRetry) },
                handleLoading = ::handleLoading,
                navigateToDetails = ::navigateToDetails
            )
        )
        tabAdapter.add(
            FavoriteCharactersEntry(
                handleError = { marvelException, onRetry -> handleError(marvelException, onRetry) },
                handleLoading = ::handleLoading,
                navigateToDetails = ::navigateToDetails
            )
        )
    }

    private fun navigateToDetails(character: MarvelCharacter, imageView: AppCompatImageView) {
        val updatedCharacter =
            viewModel.retrieveRealFavoriteStatus(character).let { newFavoriteStatus ->
                character.copy(isFavorite = newFavoriteStatus)
            }

        val intent = Intent(this, CharacterDetailsActivity::class.java).apply {
            putParams(bundleOf(CHARACTER_DETAILS_PARAMS to updatedCharacter))
        }

        viewModel.storeCharacterStatusBeforeNavigateToDetails(
            character = updatedCharacter,
            imageView = imageView
        )

        startActivityForResult(intent, REQUEST_CODE)
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

    private fun updateStarViewOfListView(
        imageView: AppCompatImageView,
        isSaved: Boolean
    ) {
        handleLoading(false)
        val imsRes = if (isSaved)
            R.drawable.ic_filled_star
        else
            R.drawable.ic_star

        AppCompatResources.getDrawable(this, imsRes).let(imageView::setImageDrawable)
    }

    private companion object {
        const val REQUEST_CODE = 1
    }
}