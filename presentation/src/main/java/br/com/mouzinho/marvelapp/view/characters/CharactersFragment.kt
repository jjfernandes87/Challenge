package br.com.mouzinho.marvelapp.view.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import br.com.mouzinho.marvelapp.R
import br.com.mouzinho.marvelapp.databinding.FragmentCharactersBinding
import br.com.mouzinho.marvelapp.extensions.FragmentExtensions.showToast
import br.com.mouzinho.marvelapp.view.main.MainViewModel
import br.com.mouzinho.marvelapp.view.main.MainViewState
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo

@AndroidEntryPoint
class CharactersFragment : Fragment() {
    private val viewModel by viewModels<CharactersViewModel>()
    private val mainViewModel by viewModels<MainViewModel>(ownerProducer = { requireParentFragment() })
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
            is CharactersViewState.Loading -> {
                binding?.layoutProgress?.isVisible = true
            }
            is CharactersViewState.CharactersLoaded -> {
                binding?.swipeRefresh?.isRefreshing = false
                binding?.layoutProgress?.isVisible = false
                adapter?.submitList(state.characters)
            }
            is CharactersViewState.Error -> {
                binding?.swipeRefresh?.isRefreshing = false
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
            val layoutManager = GridLayoutManager(context, 2)
            if (adapter == null) adapter = CharactersAdapter(viewModel::updateCharacterFromFavorites, {/*TODO*/ })
            recyclerView.adapter = adapter
            recyclerView.layoutManager = layoutManager
        }
    }
}