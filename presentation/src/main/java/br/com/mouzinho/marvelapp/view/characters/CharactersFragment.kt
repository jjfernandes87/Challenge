package br.com.mouzinho.marvelapp.view.characters

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
import br.com.mouzinho.marvelapp.databinding.FragmentCharactersBinding
import br.com.mouzinho.marvelapp.extensions.showDialog
import br.com.mouzinho.marvelapp.extensions.showToast
import br.com.mouzinho.marvelapp.navigator.Navigator
import br.com.mouzinho.marvelapp.util.hasInternetConnection
import br.com.mouzinho.marvelapp.view.characterDetails.CharacterDetailsFragment
import br.com.mouzinho.marvelapp.view.main.MainViewModel
import br.com.mouzinho.marvelapp.view.main.MainViewState
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo

@AndroidEntryPoint
class CharactersFragment : Fragment() {
    private val viewModel by viewModels<CharactersViewModel>()
    private val mainViewModel by activityViewModels<MainViewModel>()
    private var adapter: CharactersAdapter? = null
    private var binding: FragmentCharactersBinding? = null
    private val disposables = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCharactersBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        viewModel.loadCharacters()
        observeViewState()
        observeMainViewState()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposables.clear()
        binding = null
        adapter = null
    }

    private fun setupUi() {
        setupRecyclerViewWithEpoxy()
        binding?.swipeRefresh?.setOnRefreshListener {
            viewModel.reloadCharacters()
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

    private fun onNextState(state: CharactersViewState) {
        when (state) {
            is CharactersViewState.FavoriteSaved -> {
                adapter?.updateFavorite(state.character)
                showToast(R.string.saved_as_favorite)
            }
            is CharactersViewState.FavoriteRemoved -> {
                adapter?.updateFavorite(state.character)
                showToast(R.string.removed_from_favorites)
            }
            is CharactersViewState.CharactersLoaded -> {
                binding?.swipeRefresh?.isRefreshing = false
                binding?.includedProgressView?.root?.isVisible = false
                adapter?.submitList(state.characters)
            }
            is CharactersViewState.ToggleLoading -> {
                binding?.includedProgressView?.root?.isVisible = state.isLoading
            }
            is CharactersViewState.ToggleEmptyView -> {
                binding?.includedEmptyView?.run {
                    root.isVisible = state.isEmpty
                    textView.setText(
                        if (state.fromSearch) R.string.not_found_on_search
                        else R.string.nothing_here
                    )
                }
            }
            is CharactersViewState.Error -> {
                binding?.run {
                    swipeRefresh.isRefreshing = false
                    includedProgressView.root.isVisible = false
                    includedEmptyView.root.isVisible = true
                    includedEmptyView.textView.text = getString(R.string.generic_error)
                }
            }
            is CharactersViewState.FavoriteUpdateError -> {
                showToast(state.message)
            }
        }
    }

    private fun onNextMainViewState(state: MainViewState) {
        when (state) {
            is MainViewState.Search -> viewModel.search(state.text)
            is MainViewState.Reload -> viewModel.reloadCharacters()
        }
    }

    private fun setupRecyclerViewWithEpoxy() {
        binding?.run {
            if (adapter == null) adapter = CharactersAdapter(viewModel::updateCharacterFromFavorites, ::goToDetails)
            recyclerView.adapter = adapter
        }
    }

    private fun goToDetails(marvelCharacter: MarvelCharacter) {
        if (context?.hasInternetConnection() == false) {
            showDialog(getString(R.string.error_title), getString(R.string.network_error))
            return
        }
        Navigator.navigateTo(CharacterDetailsFragment.newInstance(marvelCharacter), marvelCharacter.name)
    }
}