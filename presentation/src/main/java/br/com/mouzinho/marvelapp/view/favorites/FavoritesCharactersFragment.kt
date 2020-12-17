package br.com.mouzinho.marvelapp.view.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import br.com.mouzinho.domain.entity.character.MarvelCharacter
import br.com.mouzinho.marvelapp.R
import br.com.mouzinho.marvelapp.databinding.FragmentFavoritesBinding
import br.com.mouzinho.marvelapp.extensions.showDialog
import br.com.mouzinho.marvelapp.extensions.showToast
import br.com.mouzinho.marvelapp.navigator.Navigator
import br.com.mouzinho.marvelapp.view.characterDetails.CharacterDetailsFragment
import br.com.mouzinho.marvelapp.view.favorites.FavoritesCharactersViewState.*
import br.com.mouzinho.marvelapp.view.main.MainViewModel
import br.com.mouzinho.marvelapp.view.main.MainViewState
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo

@AndroidEntryPoint
class FavoritesCharactersFragment : Fragment() {
    private var binding: FragmentFavoritesBinding? = null
    private val viewModel by viewModels<FavoritesCharactersViewModel>()
    private val mainViewModel by activityViewModels<MainViewModel>()
    private val disposables = CompositeDisposable()
    private var adapter: FavoritesAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        observeViewState()
        observeMainViewState()
        viewModel.loadFavorites()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposables.clear()
        binding = null
        adapter = null
    }

    private fun setupUi() {
        binding?.run {
            swipeRefresh.setOnRefreshListener { viewModel.loadFavorites() }
            if (adapter == null) adapter = FavoritesAdapter(viewModel::removeFavorite, viewModel::loadMarvelCharacterInfo)
            recyclerView.adapter = adapter
        }
    }

    private fun observeViewState() {
        viewModel
            .stateObservable
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::onNextState)
            .addTo(disposables)
    }

    private fun observeMainViewState() {
        mainViewModel
            .stateObservable
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::onNextMainViewState)
            .addTo(disposables)
    }

    private fun onNextMainViewState(state: MainViewState) {
        when (state) {
            is MainViewState.Search -> viewModel.search(state.text)
            else -> Unit
        }
    }

    private fun onNextState(state: FavoritesCharactersViewState) {
        binding?.run {
            when (state) {
                is ShowFavorites -> {
                    swipeRefresh.isRefreshing = false
                    includedEmptyView.root.isVisible = state.favorites.isEmpty()
                    includedEmptyView.textView.setText(
                        if (state.fromSearch) R.string.not_found_on_search
                        else R.string.nothing_here
                    )
                    adapter?.submitList(state.favorites)
                }
                is ShowError -> showDialog(getString(R.string.error_title), state.message)
                is ShowRemovedMessage -> showToast(R.string.removed_from_favorites)
                is ReloadCharacters -> mainViewModel.reloadCharacters()
                is GoToDetails -> goToCharacterDetails(state.marvelCharacter)
                is ToggleLoading -> includedProgressView.root.isVisible = state.isLoading
            }
        }
    }

    private fun goToCharacterDetails(marvelCharacter: MarvelCharacter) {
        Navigator.navigateTo(CharacterDetailsFragment.newInstance(marvelCharacter), marvelCharacter.name)
    }
}