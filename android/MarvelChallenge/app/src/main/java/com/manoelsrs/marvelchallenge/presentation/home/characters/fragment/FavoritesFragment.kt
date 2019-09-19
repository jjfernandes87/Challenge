package com.manoelsrs.marvelchallenge.presentation.home.characters.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import com.manoelsrs.marvelchallenge.R
import com.manoelsrs.marvelchallenge.core.common.BaseFragment
import com.manoelsrs.marvelchallenge.model.Character
import com.manoelsrs.marvelchallenge.presentation.home.characters.fragment.adapter.ItemViewPagerAdapter
import com.manoelsrs.marvelchallenge.presentation.home.characters.fragment.viewmodel.CharactersViewState
import com.manoelsrs.marvelchallenge.presentation.home.characters.fragment.viewmodel.FavoritesViewModel
import dagger.android.support.AndroidSupportInjection
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_characters.*
import javax.inject.Inject

class FavoritesFragment : BaseFragment() {

    @Inject
    lateinit var viewModel: FavoritesViewModel

    @Inject
    lateinit var listener: Observable<String>

    private val adapter = ItemViewPagerAdapter()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
        listener
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    viewModel.updateItems(it)
                    watchViewModel(adapter)
                },
                { /** TODO */ }
            ).also { addDisposable(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_characters, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvCharacters.layoutManager = GridLayoutManager(context, 2)
        rvCharacters.adapter = adapter
        adapter.setOnClickListener { Log.d("PERSONAGEM", it.name) }
        swipeRefreshLayout.setOnRefreshListener { swipeRefreshLayout.isRefreshing = false }
        observeViewState()
        watchViewModel(adapter)
    }

    private fun watchViewModel(adapter: ItemViewPagerAdapter) {
        viewModel.characters.observe(this, Observer { item: PagedList<Character> ->
            adapter.submitList(item)
            setEmptyView(tvEmpy, item.isEmpty(), R.string.empty_favorites_message)
        })
    }

    private fun observeViewState() {
        viewModel.viewState().observe(this, Observer { viewState: CharactersViewState ->
            return@Observer when (viewState) {
                is CharactersViewState.Error -> {
                    swipeRefreshLayout.isRefreshing = false
                    /** Todo */
                }
                is CharactersViewState.Loading -> {
                    swipeRefreshLayout.isRefreshing = viewState.isLoading
                }
            }
        })
    }

    override fun onDetach() {
        dispose()
        super.onDetach()
    }
}