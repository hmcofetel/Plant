package com.project.plantapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.plantapp.R
import com.project.plantapp.model.ProfileDetailItem

class ProfileDetailItemAdapt(private val detailList: ArrayList<ProfileDetailItem>, val listener: MyClickListener):RecyclerView.Adapter<ProfileDetailItemAdapt.MyViewHolder>() {

    inner class MyViewHolder(itemView:View): RecyclerView.ViewHolder(itemView){
        val imageView: ImageView = itemView.findViewById(R.id.ivItemProfileDetail)
        val textView: TextView = itemView.findViewById(R.id.tvItemProfileDetail)

        init {
            itemView.setOnClickListener{
                val position = adapterPosition
                listener.onClick(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_profile_detail, parent,false))
    }

    override fun getItemCount(): Int {
        return detailList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val detail = detailList[position]
        holder.textView.text = detail.name
        holder.imageView.setImageResource(detail.image)
    }

    interface MyClickListener{
        fun onClick(position: Int)
    }

}