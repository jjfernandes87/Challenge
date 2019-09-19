package com.manoelsrs.marvelchallenge.presentation.home.characters.fragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.manoelsrs.marvelchallenge.R
import com.manoelsrs.marvelchallenge.model.Character
import com.manoelsrs.marvelchallenge.presentation.home.characters.fragment.adapter.viewholder.TabViewHolder

class ItemViewPagerAdapter : PagedListAdapter<Character, TabViewHolder>(diffCallback) {

    override fun onBindViewHolder(holder: TabViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TabViewHolder =
        TabViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.character_item, parent, false)
        )

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Character>() {
            override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean =
                oldItem == newItem
        }
    }
}