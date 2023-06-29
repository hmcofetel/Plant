package com.project.plantapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.SectionIndexer
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.plantapp.R
import com.project.plantapp.model.Articles
import com.project.plantapp.util.Helpers.Companion.sectionsHelper
import java.util.Locale

interface OnSpecieSIndexItemListener {
    fun onClickItem(item: String)

}

class SpecieIndexAdapter(mDataArray: ArrayList<String>?, private val itemListener: OnSpecieSIndexItemListener):
    RecyclerView.Adapter<SpecieIndexAdapter.ViewHolder>(), SectionIndexer, Filterable {

    private val mSections = "ABCDEFGHIJKLMNOPQRSTUVWXYZ#"
    private var sectionsTranslator = HashMap<Int, Int>()
    private var mSectionPositions: ArrayList<Int>? = null
    private var _mDataArray = mDataArray
    private var _mDataArrayUnFill = mDataArray

    override fun getItemCount(): Int {
        return _mDataArray?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_specie_index, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = _mDataArray!![position]
        holder.mTextView.text = item
        holder.mTextView.setOnClickListener{
            itemListener.onClickItem(item)
        }
    }

    override fun getSectionForPosition(position: Int): Int {
        return 0
    }

    override fun getSections(): Array<String> {
        val sections: MutableList<String> = ArrayList(27)
        val alphabetFull = ArrayList<String>()
        mSectionPositions = ArrayList()
        run {
            var i = 0
            val size = _mDataArray!!.size
            while (i < size) {
                val section = _mDataArray!![i][0].toString().uppercase(Locale.getDefault())
                if (!sections.contains(section)) {
                    sections.add(section)
                    mSectionPositions?.add(i)
                }
                i++
            }
        }
        for (element in mSections) {
            alphabetFull.add(element.toString())
        }
        sectionsTranslator = sectionsHelper(sections, alphabetFull)
        return alphabetFull.toTypedArray()
    }

    override fun getPositionForSection(sectionIndex: Int): Int {
        return mSectionPositions!![sectionsTranslator[sectionIndex]!!]
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mTextView: TextView
        init {
            mTextView = itemView.findViewById(R.id.tv_alphabet)
        }
    }

    override fun getFilter(): Filter {
        return object: Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val strSearch = constraint.toString()
                var types = ArrayList<String>()
                if (strSearch.isNotEmpty())
                {

                    for (type in _mDataArrayUnFill!!)
                    {
                        if (type.lowercase(Locale.ROOT)
                                .contains(strSearch.lowercase(Locale.ROOT))){
                            types.add(type)
                        }
                    }
                }
                else
                {
                    types = _mDataArrayUnFill!!
                }
                _mDataArray =types
                val result = FilterResults()
                result.values = types
                return result
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

            }

        }
    }

}