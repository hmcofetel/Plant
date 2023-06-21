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


class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var _storageRef = FirebaseStorage.getInstance().reference
    private var _db = FirebaseFirestore.getInstance()
    private var _usersDB = _db.collection("users")

    companion object {
        fun from(parent: ViewGroup): ArticleViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(
                R.layout.item_article, parent, false
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

        tvDate.text = article.date
        tvTitle.text = article.title

        _usersDB.document(article.author).get(Source.CACHE).addOnSuccessListener { document ->
            val profile = document.data?.get("profile") as HashMap<*, *>
            val author = "${profile["first"] as String} ${profile["last"] as String}"
            tvAuthor.text = author

            GlideApp.with(itemView.context)
                .load(_storageRef.child("avatars").child(profile["avt"] as String)).centerCrop()
                .into(ivAvatar)

        }





        GlideApp.with(itemView.context).load(_storageRef.child("articles").child(article.img)).centerCrop().into(ivTile)


        Log.v("hmco: ", "Reload cycle view")


        itemView.setOnClickListener { callback.onClickItem(article) }
    }
}


