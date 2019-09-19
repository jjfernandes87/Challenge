package com.manoelsrs.marvelchallenge.presentation.home.details.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.manoelsrs.marvelchallenge.R
import com.manoelsrs.marvelchallenge.model.Data
import com.manoelsrs.marvelchallenge.presentation.home.details.adapters.viewholder.DataViewHolder

class DataListAdapter : RecyclerView.Adapter<DataViewHolder>() {

    private var items: List<DataModelContract> = listOf()

    fun setListItems(content: List<Data>) {
        items = content.map { DataModel(it) }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        return DataViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.data_item, parent, false)
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(items[position])
    }
}