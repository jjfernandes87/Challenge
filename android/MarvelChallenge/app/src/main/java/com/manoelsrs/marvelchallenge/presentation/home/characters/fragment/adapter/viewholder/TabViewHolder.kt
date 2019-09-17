package com.manoelsrs.marvelchallenge.presentation.home.characters.fragment.adapter.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.manoelsrs.marvelchallenge.presentation.home.characters.fragment.adapter.ItemViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.character_item.view.*

class TabViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(viewModel: ItemViewModel) {
        itemView.tvCharacterName.text = viewModel.name
        Picasso.get()
            .load("${viewModel.photo}/standard_xlarge.${viewModel.photoExtension}")
            .into(itemView.ivCharacterPhoto)
    }
}