package com.project.plantapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.plantapp.R
import com.project.plantapp.model.Articles
import com.bumptech.glide.Glide

class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)  {

    companion object {
        fun from(parent: ViewGroup): ArticleViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view =
                layoutInflater.inflate(
                    R.layout.item_article,
                    parent,
                    false
                )
            return ArticleViewHolder(view)
        }
    }
    fun bindData(article: Articles, callback: OnArticleItemListener) {
        val tvAuthor = itemView.findViewById<TextView>(R.id.tv_name)
        val tvDate = itemView.findViewById<TextView>(R.id.tv_date)
        val tvTitle = itemView.findViewById<TextView>(R.id.tv_title)
        val ivAvatar = itemView.findViewById<ImageView>(R.id.iv_avatar)
        val ivTile = itemView.findViewById<ImageView>(R.id.iv_title)

        tvAuthor.text = article.author
        tvDate.text = article.date
        tvTitle.text = article.title

        // hmco: Should be implement by Firebase at here instead of Glide
        Glide.with(itemView.context).load(article.avt).centerCrop().into(ivAvatar)
        Glide.with(itemView.context).load(article.img).centerCrop().into(ivTile)

        itemView.setOnClickListener { callback.onClickItem(article) }
    }
}