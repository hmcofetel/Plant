package com.project.plantapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import com.google.firebase.storage.FirebaseStorage
import com.project.plantapp.R
import com.project.plantapp.model.Species
import com.project.plantapp.util.GlideApp

class FavoriteSpeciesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var _storageRef = FirebaseStorage.getInstance().reference
    private var _db = FirebaseFirestore.getInstance()
    private var _usersDB = _db.collection("users")
    private var _plants = _db.collection("plants")

    companion object {
        fun from(parent: ViewGroup): FavoriteSpeciesViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(
                R.layout.item_favorite_species, parent, false
            )
            return FavoriteSpeciesViewHolder(view)
        }
    }

    fun bindData(species: Species, callback: OnSpeciesFavoriteItemListener) {
        val tvTitle = itemView.findViewById<TextView>(R.id.tv_favorite_species)
        val ivTile = itemView.findViewById<ImageView>(R.id.iv_favorite_species)
        tvTitle.text = species.title

        GlideApp.with(itemView.context)
            .load(_storageRef.child("plants").child(species.image)).centerCrop()
            .into(ivTile)



        itemView.setOnClickListener { callback.onClickItem(species) }
    }
}
