package com.project.plantapp.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.project.plantapp.model.Species

interface OnSpeciesItemListener {
    fun onClickItem(item: Species)

}

class SpeciesAdapter (private val itemListener: OnSpeciesItemListener) : ListAdapter<Species, SpeciesViewHolder>(SpeciesDiffUtil()){


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SpeciesViewHolder {
        return SpeciesViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SpeciesViewHolder, position: Int) {
        val item = getItem(position)
        holder.bindData(item, itemListener)
    }


    class SpeciesDiffUtil : DiffUtil.ItemCallback<Species>() {
        override fun areItemsTheSame(oldItem: Species, newItem: Species): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Species,
            newItem: Species
        ): Boolean {
            return oldItem.title == newItem.title && oldItem.image == newItem.image
        }

    }


}