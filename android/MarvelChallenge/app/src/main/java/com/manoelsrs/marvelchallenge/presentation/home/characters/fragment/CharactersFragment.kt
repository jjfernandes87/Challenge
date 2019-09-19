package com.manoelsrs.marvelchallenge.presentation.home.characters.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.manoelsrs.marvelchallenge.R
import com.manoelsrs.marvelchallenge.model.Character
import com.manoelsrs.marvelchallenge.presentation.home.characters.fragment.adapter.ItemViewPagerAdapter
import com.manoelsrs.marvelchallenge.presentation.home.characters.fragment.viewmodel.CharactersViewModel
import com.manoelsrs.marvelchallenge.presentation.home.characters.fragment.viewmodel.CharactersViewState
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_characters.*
import javax.inject.Inject

class CharactersFragment : Fragment() {

    @Inject
    lateinit var viewModel: CharactersViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_characters, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setCharactersList()
        observeViewState()
        swipeRefreshLayout.setOnRefreshListener { viewModel.updateItems() }
    }

    private fun setCharactersList() {
        val adapter = ItemViewPagerAdapter()
        rvCharacters.layoutManager = GridLayoutManager(context, 2)
        rvCharacters.adapter = adapter

        rvCharacters.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!rvCharacters.canScrollVertically(1)) viewModel.loadMoreItems()
            }
        })

        viewModel.characters.observe(this, Observer { item: PagedList<Character> ->
            adapter.submitList(item)
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
}