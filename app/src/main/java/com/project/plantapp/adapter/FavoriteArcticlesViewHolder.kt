package com.project.plantapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.plantapp.R
import com.project.plantapp.model.Articles
import com.bumptech.glide.Glide
import com.bumptech.glide.module.AppGlideModule
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import com.google.firebase.storage.FirebaseStorage
import com.project.plantapp.util.GlideApp


class FavoriteArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var _storageRef = FirebaseStorage.getInstance().reference
    private var _db = FirebaseFirestore.getInstance()
    private var _usersDB = _db.collection("users")

    companion object {
        fun from(parent: ViewGroup): FavoriteArticleViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(
                R.layout.item_favorite_articles, parent, false
            )
            return FavoriteArticleViewHolder(view)
        }
    }

    fun bindData(article: Articles, callback: OnFavoriteArticleItemListener) {
        val tvTitle = itemView.findViewById<TextView>(R.id.tv_favorite_articles)
        val ivTile = itemView.findViewById<ImageView>(R.id.iv_favorite_articles)
        tvTitle.text = article.title
        GlideApp.with(itemView.context).load(_storageRef.child("articles").child(article.img)).centerCrop().into(ivTile)

        Log.v("hmco: ", "Reload cycle view")


        itemView.setOnClickListener { callback.onClickItem(article) }
    }
}


