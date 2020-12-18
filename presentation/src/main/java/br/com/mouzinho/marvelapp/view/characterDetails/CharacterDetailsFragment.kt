package br.com.mouzinho.marvelapp.view.characterDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import br.com.mouzinho.domain.entity.character.MarvelCharacter
import br.com.mouzinho.domain.entity.comic.MediaDetails
import br.com.mouzinho.marvelapp.*
import br.com.mouzinho.marvelapp.databinding.FragmentCharacterDetailsBinding
import br.com.mouzinho.marvelapp.databinding.ViewHolderImageWithTextBinding
import br.com.mouzinho.marvelapp.databinding.ViewHolderMediaDetailsBinding
import br.com.mouzinho.marvelapp.extensions.loadImage
import br.com.mouzinho.marvelapp.extensions.showToast
import br.com.mouzinho.marvelapp.navigator.Navigator
import br.com.mouzinho.marvelapp.view.main.MainViewModel
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.carousel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import java.util.*

@AndroidEntryPoint
class CharacterDetailsFragment : Fragment() {
    private var binding: FragmentCharacterDetailsBinding? = null
    private val viewModel by viewModels<CharacterDetailsViewModel>()
    private val mainViewModel by activityViewModels<MainViewModel>()
    private val marvelCharacter by lazy { arguments?.getSerializable(MARVEL_CHARACTER_EXTRA) as? MarvelCharacter }
    private val disposables = CompositeDisposable()
    private var favoriteItem: MenuItem? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCharacterDetailsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewState()
        marvelCharacter?.let(viewModel::loadMedia)
        setupToolbar()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.action_favorite) {
            marvelCharacter?.let(viewModel::updateFavoriteStatus)
            true
        } else super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposables.clear()
    }

    private fun observeViewState() {
        viewModel.stateObservable
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { state ->
                when (state) {
                    is CharacterDetailsViewState.ShowDetails -> renderView(state)
                    is CharacterDetailsViewState.ToggleLoading -> binding?.includedProgressView?.root?.isVisible = state.isLoading
                    is CharacterDetailsViewState.ToggleFavorite -> {
                        mainViewModel.reloadCharacters()
                        showToast(
                            getString(if (state.isFavorite) R.string.saved_as_favorite else R.string.removed_from_favorites)
                        )
                        updateFavoriteIcon(state.isFavorite)
                    }
                }
            }
            .addTo(disposables)
    }

    private fun renderView(details: CharacterDetailsViewState.ShowDetails) {
        binding?.epoxyRecyclerView?.run {
            setRemoveAdapterWhenDetachedFromWindow(true)
            withModels {
                buildTopView(details).addTo(this)
                header {
                    id(HEADER_COMICS_ID)
                    text(context.getString(R.string.comics))
                }
                carousel {
                    id(CAROUSEL_COMICS_ID)
                    models(buildMediaModels(details.comics))
                }
                header {
                    id(HEADER_SERIES_ID)
                    text(context.getString(R.string.series))
                }
                carousel {
                    id(CAROUSEL_SERIES_ID)
                    models(buildMediaModels(details.series))
                }
            }
        }
    }

    private fun buildTopView(details: CharacterDetailsViewState.ShowDetails): EpoxyModel<*> {
        return ImageWithTextBindingModel_()
            .id(HEADER_TOP_ID)
            .hasDescription(details.description.isNotBlank())
            .text(details.description)
            .onBind { _, view, _ ->
                (view.dataBinding as? ViewHolderImageWithTextBinding)?.imageViewHeader?.let { imageView ->
                    loadImage(details.headerImageUrl, imageView)
                }
            }
    }

    private fun buildMediaModels(media: List<MediaDetails>): List<EpoxyModel<*>> {
        return if (media.isNotEmpty()) media.map {
            MediaDetailsBindingModel_()
                .id(it.id)
                .title(it.title)
                .onBind { _, view, _ ->
                    (view.dataBinding as? ViewHolderMediaDetailsBinding)?.imageView?.run {
                        loadImage(it.thumbnail.landscapeMediumUrl, this)
                    }
                }
        } else {
            listOf(
                TextBindingModel_()
                    .id(RANDOM_ID)
                    .text(getString(R.string.empty_media_message))
            )
        }
    }

    private fun setupToolbar() {
        binding?.includedToolbar?.toolbar?.run {
            title = marvelCharacter?.name ?: getString(R.string.app_name)
            setNavigationIcon(R.drawable.ic_arrow_back)
            setNavigationOnClickListener { Navigator.popUp() }
            inflateMenu(R.menu.menu_favorite)
            favoriteItem = menu.findItem(R.id.action_favorite)
            updateFavoriteIcon(marvelCharacter?.isFavorite ?: false)
            setOnMenuItemClickListener(::onOptionsItemSelected)
        }
    }

    private fun updateFavoriteIcon(isFavorite: Boolean) {
        favoriteItem?.setIcon(if (isFavorite) android.R.drawable.btn_star_big_on else android.R.drawable.btn_star_big_off)
    }

    companion object {
        private const val MARVEL_CHARACTER_EXTRA = "MARVEL_CHARACTER_EXTRA"
        private const val HEADER_TOP_ID = "HEADER_TOP_ID"
        private const val HEADER_COMICS_ID = "HEADER_COMICS_ID"
        private const val HEADER_SERIES_ID = "HEADER_SERIES_ID"
        private const val CAROUSEL_SERIES_ID = "CAROUSEL_SERIES"
        private const val CAROUSEL_COMICS_ID = "CAROUSEL_COMICS"
        private val RANDOM_ID get() = "${System.currentTimeMillis()}${UUID.randomUUID()}"

        fun newInstance(marvelCharacter: MarvelCharacter) = CharacterDetailsFragment().apply {
            arguments = bundleOf(MARVEL_CHARACTER_EXTRA to marvelCharacter)
        }
    }
}