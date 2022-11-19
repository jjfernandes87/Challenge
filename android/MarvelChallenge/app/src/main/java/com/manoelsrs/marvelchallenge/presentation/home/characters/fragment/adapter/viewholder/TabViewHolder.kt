package com.manoelsrs.marvelchallenge.presentation.home.characters.fragment.adapter.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.manoelsrs.marvelchallenge.R
import com.manoelsrs.marvelchallenge.model.Character
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.character_item.view.*

class TabViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(character: Character?, onClick: (Character) -> Unit) {
        character?.run {
            itemView.tvCharacterName.text = name
            Picasso.get()
                .load("${photo}/standard_xlarge.${photoExtension}")
                .error(R.mipmap.ic_launcher)
                .into(itemView.ivCharacterPhoto)
            itemView.setOnClickListener { onClick(character) }
        }
    }
}