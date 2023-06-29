package com.project.plantapp.adapter

import android.util.Log
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.compose.ui.text.toLowerCase
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.project.plantapp.model.Articles
import java.util.Locale


interface OnArticleItemListener {
    fun onClickItem(item: Articles)

}

class ArticleAdapter (private val itemListener: OnArticleItemListener) : ListAdapter<Articles, ArticleViewHolder>(ArticleDiffUtil()) , Filterable{
    var unfilteredList: MutableList<Articles> =  currentList

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

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val strSearch = constraint.toString()
                if (strSearch.isNotEmpty())
                {
                    val articles = ArrayList<Articles>()
                    for (article in unfilteredList)
                    {
                        if (article.title.lowercase(Locale.ROOT)
                                .contains(strSearch.lowercase(Locale.ROOT))){
                            articles.add(article)
                        }
                    }
                    submitList(articles)
                }
                else
                {
                    submitList(unfilteredList)
                }
                val result = FilterResults()
                result.values = currentList
                return result
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

            }

        }
    }
}