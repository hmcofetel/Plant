package com.project.plantapp.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.project.plantapp.model.Articles


interface OnFavoriteArticleItemListener {
    fun onClickItem(item: Articles)

}

class FavoriteArticlesAdapter (private val itemListener: OnFavoriteArticleItemListener) : ListAdapter<Articles, FavoriteArticleViewHolder>(FavoriteArticleDiffUtil()){


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteArticleViewHolder {
        return FavoriteArticleViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: FavoriteArticleViewHolder, position: Int) {
        val image = getItem(position)
        holder.bindData(image, itemListener)
    }


    class FavoriteArticleDiffUtil : DiffUtil.ItemCallback<Articles>() {
        override fun areItemsTheSame(oldItem: Articles, newItem: Articles): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Articles,
            newItem: Articles
        ): Boolean {
            return oldItem.title == newItem.title && oldItem.img == newItem.img
        }

    }
}