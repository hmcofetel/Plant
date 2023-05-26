package com.project.plantapp.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.project.plantapp.model.Articles


interface OnArticleItemListener {
    fun onClickItem(item: Articles)

}

class ArticleAdapter (private val itemListener: OnArticleItemListener) : ListAdapter<Articles, ArticleViewHolder>(ArticleDiffUtil()){


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArticleViewHolder {
        return ArticleViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val image = getItem(position)
        holder.bindData(image, itemListener)
    }


    class ArticleDiffUtil : DiffUtil.ItemCallback<Articles>() {
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