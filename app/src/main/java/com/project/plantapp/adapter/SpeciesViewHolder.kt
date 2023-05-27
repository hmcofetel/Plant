package com.project.plantapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.plantapp.R
import com.project.plantapp.model.Species

class SpeciesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    companion object {
        fun from(parent: ViewGroup): SpeciesViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view =
                layoutInflater.inflate(
                    R.layout.item_species_detail,
                    parent,
                    false
                )
            return SpeciesViewHolder(view)
        }
    }
    fun bindData(specie: Species, callback: OnSpeciesItemListener) {

        val tvTitle = itemView.findViewById<TextView>(R.id.speciesTitle)
        val tvKingdom = itemView.findViewById<TextView>(R.id.kingdomContent)
        val tvFamily = itemView.findViewById<TextView>(R.id.familyContent)
        val tvDescription = itemView.findViewById<TextView>(R.id.descriptionContent)
        val imSpecie =  itemView.findViewById<ImageView>(R.id.ivSpecie)

        tvTitle.text = specie.title
        tvKingdom.text = specie.kingdom
        tvFamily.text = specie.family
        tvDescription.text = specie.description
        
        Glide.with(itemView.context).load(specie.image).centerCrop().into(imSpecie)
        Log.v("hmcous: ",specie.image)
        itemView.setOnClickListener { callback.onClickItem(specie) }
    }
}