package com.manoelsrs.marvelchallenge.presentation.home.characters.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import com.manoelsrs.marvelchallenge.R
import com.manoelsrs.marvelchallenge.model.Character
import com.manoelsrs.marvelchallenge.presentation.home.characters.fragment.adapter.ItemViewPagerAdapter
import com.manoelsrs.marvelchallenge.presentation.home.characters.fragment.viewmodel.FavoritesViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_characters.*
import javax.inject.Inject

class FavoritesFragment : Fragment() {

    @Inject
    lateinit var viewModel: FavoritesViewModel

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
        val adapter = ItemViewPagerAdapter()
        rvCharacters.layoutManager = GridLayoutManager(context, 2)
        rvCharacters.adapter = adapter
        adapter.setOnClickListener { Log.d("PERSONAGEM", it.name) }
        swipeRefreshLayout.setOnRefreshListener { swipeRefreshLayout.isRefreshing = false }
        viewModel.characters.observe(this, Observer { item: PagedList<Character> ->
            adapter.submitList(item)
        })
    }
}