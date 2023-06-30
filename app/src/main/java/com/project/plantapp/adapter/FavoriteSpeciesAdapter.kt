package com.project.plantapp.adapter

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.project.plantapp.model.Articles
import com.project.plantapp.model.Species


interface OnSpeciesFavoriteItemListener {
    fun onClickItem(item: Species)

}

class FavoriteSpeciesAdapter (private val itemListener: OnSpeciesFavoriteItemListener) : ListAdapter<Species, FavoriteSpeciesViewHolder>(SpeciesFavoriteDiffUtil()){


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteSpeciesViewHolder {
        return FavoriteSpeciesViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: FavoriteSpeciesViewHolder, position: Int) {
        val image = getItem(position)

        holder.bindData(image, itemListener)
    }


    class SpeciesFavoriteDiffUtil : DiffUtil.ItemCallback<Species>() {
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