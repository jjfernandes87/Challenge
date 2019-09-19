package com.manoelsrs.marvelchallenge.presentation.home.details.adapters.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.manoelsrs.marvelchallenge.presentation.home.details.adapters.DataModelContract
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.data_item.view.*

class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(model: DataModelContract) {
        with(model) {
            Picasso.get()
                .load("${photo}/standard_xlarge.${photoExtension}")
                .into(itemView.ivDataPhoto)
            itemView.tvDataTitle.text = title
        }
    }
}