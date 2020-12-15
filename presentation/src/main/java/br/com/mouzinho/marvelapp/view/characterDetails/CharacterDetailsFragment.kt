package br.com.mouzinho.marvelapp.view.characterDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import br.com.mouzinho.domain.entity.character.MarvelCharacter
import br.com.mouzinho.domain.entity.comic.ComicDetails
import br.com.mouzinho.marvelapp.ComicDetailsBindingModel_
import br.com.mouzinho.marvelapp.R
import br.com.mouzinho.marvelapp.databinding.FragmentCharacterDetailsBinding
import br.com.mouzinho.marvelapp.databinding.ViewHolderComicDetailsBinding
import br.com.mouzinho.marvelapp.databinding.ViewHolderImageWithTextBinding
import br.com.mouzinho.marvelapp.extensions.loadImage
import br.com.mouzinho.marvelapp.header
import br.com.mouzinho.marvelapp.imageWithText
import br.com.mouzinho.marvelapp.navigator.Navigator
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.carousel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo

@AndroidEntryPoint
class CharacterDetailsFragment : Fragment() {
    private var binding: FragmentCharacterDetailsBinding? = null
    private val viewModel by viewModels<CharacterDetailsViewModel>()
    private val marvelCharacter by lazy { arguments?.getSerializable(MARVEL_CHARACTER_EXTRA) as? MarvelCharacter }
    private val disposables = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCharacterDetailsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewState()
        marvelCharacter?.let(viewModel::loadComics)
        setupToolbar()
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
                }
            }
            .addTo(disposables)
    }

    private fun renderView(details: CharacterDetailsViewState.ShowDetails) {
        binding?.epoxyRecyclerView?.run {
            setRemoveAdapterWhenDetachedFromWindow(true)
            withModels {
                imageWithText {
                    id("headerTop")
                    hasDescription(details.description.isNotBlank())
                    text(details.description)
                    onBind { model, view, position ->
                        (view.dataBinding as? ViewHolderImageWithTextBinding)?.imageViewHeader?.let { imageView ->
                            loadImage(details.headerImageUrl, imageView)
                        }
                    }
                }
                header {
                    id("headerComics")
                    text("Comics")
                }
                carousel {
                    id("carouselComics")
                    models(buildComicsModels(details.comics))
                }
                header {
                    id("headerSeries")
                    text("Series")
                }
//                carousel {
//                    id("carouselSeries")
//                    models()
//                }
            }
        }
    }

    private fun buildComicsModels(comics: List<ComicDetails>): List<EpoxyModel<*>> {
        return comics.map {
            ComicDetailsBindingModel_()
                .id(it.id)
                .title(it.title)
                .onBind { _, view, _ ->
                    (view.dataBinding as? ViewHolderComicDetailsBinding)?.imageView?.run {
                        loadImage(it.thumbnail.landscapeMediumUrl, this)
                    }
                }
        }
    }

    private fun buildSeriesModels(comics: List<ComicDetails>): List<EpoxyModel<*>> {
        return comics.map {
            ComicDetailsBindingModel_()
                .id(it.id)
                .title(it.title)
                .onBind { _, view, _ ->
                    (view.dataBinding as? ViewHolderComicDetailsBinding)?.imageView?.run {
                        loadImage(it.thumbnail.landscapeMediumUrl, this)
                    }
                }
        }
    }

    private fun setupToolbar() {
        binding?.includedToolbar?.toolbar?.run {
            title = marvelCharacter?.name ?: getString(R.string.app_name)
            setNavigationIcon(R.drawable.ic_arrow_back)
            setNavigationOnClickListener { Navigator.popUp() }
        }
    }

    companion object {
        private const val MARVEL_CHARACTER_EXTRA = "MARVEL_CHARACTER_EXTRA"

        fun newInstance(marvelCharacter: MarvelCharacter) = CharacterDetailsFragment().apply {
            arguments = bundleOf(MARVEL_CHARACTER_EXTRA to marvelCharacter)
        }
    }
}