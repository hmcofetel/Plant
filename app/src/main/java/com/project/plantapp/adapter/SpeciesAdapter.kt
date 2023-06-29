package com.project.plantapp.adapter

import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.project.plantapp.model.Articles
import com.project.plantapp.model.Species
import java.util.Locale

interface OnSpeciesItemListener {
    fun onClickItem(item: Species)

}

class SpeciesAdapter (private val itemListener: OnSpeciesItemListener) : ListAdapter<Species, SpeciesViewHolder>(SpeciesDiffUtil()), Filterable{
    var unfilteredList: List<Species> =  currentList

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SpeciesViewHolder {
        return SpeciesViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SpeciesViewHolder, position: Int) {
        val item = getItem(position)
        holder.bindData(item, itemListener)
    }


    class SpeciesDiffUtil : DiffUtil.ItemCallback<Species>() {
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

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val strSearch = constraint.toString()
                if (strSearch.isNotEmpty())
                {
                    val species = ArrayList<Species>()
                    for (article in unfilteredList)
                    {
                        if (article.title.lowercase(Locale.ROOT)
                                .contains(strSearch.lowercase(Locale.ROOT))){
                            species.add(article)
                        }
                    }
                    submitList(species)
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