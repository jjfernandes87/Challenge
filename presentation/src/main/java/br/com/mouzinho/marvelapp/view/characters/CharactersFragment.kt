package br.com.mouzinho.marvelapp.view.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import br.com.mouzinho.marvelapp.databinding.FragmentCharactersBinding
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo

@AndroidEntryPoint
class CharactersFragment : Fragment() {
    private val viewModel by viewModels<CharactersViewModel>()
    private val epoxyController by lazy { CharactersPagingController({}, {}) }
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposables.clear()
        binding = null
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
            .subscribe(::render)
            .addTo(disposables)
    }

    private fun render(state: CharactersViewState) {
        epoxyController.loadStatus = when {
            state.loading -> LoadMarvelCharactersStatus.LOAD
            state.hasError -> LoadMarvelCharactersStatus.ERROR
            state.reloaded -> LoadMarvelCharactersStatus.RELOAD
            else -> LoadMarvelCharactersStatus.LOADED
        }
        epoxyController.submitList(state.characters)
        binding?.swipeRefresh?.isRefreshing = false
    }

    private fun setupRecyclerViewWithEpoxy() {
        binding?.run {
            val layoutManager = GridLayoutManager(context, 2)
            epoxyController.spanCount = 2
            recyclerView.adapter = epoxyController.adapter
            layoutManager.spanSizeLookup = epoxyController.spanSizeLookup
            recyclerView.layoutManager = layoutManager
            recyclerView.setRemoveAdapterWhenDetachedFromWindow(true)
        }
    }
}