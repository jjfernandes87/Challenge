package com.manoelsrs.marvelchallenge.presentation.home.characters.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.manoelsrs.marvelchallenge.R
import com.manoelsrs.marvelchallenge.model.Character
import com.manoelsrs.marvelchallenge.presentation.home.characters.adapter.viewholder.TabViewHolder

class ItemViewPagerAdapter : RecyclerView.Adapter<TabViewHolder>() {

    private var items: List<ItemViewModel> = listOf()

    fun setItems(newContent: List<Character>) {
        items = newContent.map { Item(it) }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TabViewHolder =
        TabViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.character_item, parent, false)
        )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: TabViewHolder, position: Int) {
        holder.bind(items[position])
    }
}