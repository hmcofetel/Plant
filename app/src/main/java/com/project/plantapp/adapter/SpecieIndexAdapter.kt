package com.project.plantapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SectionIndexer
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.plantapp.R
import com.project.plantapp.util.Helpers.Companion.sectionsHelper
import java.util.Locale

interface OnSpecieSIndexItemListener {
    fun onClickItem(item: String)

}

class SpecieIndexAdapter(private val mDataArray: ArrayList<String>?, val itemListener: OnSpecieSIndexItemListener):
    RecyclerView.Adapter<SpecieIndexAdapter.ViewHolder>(), SectionIndexer {

    private val mSections = "ABCDEFGHIJKLMNOPQRSTUVWXYZ#"
    private var sectionsTranslator = HashMap<Int, Int>()
    private var mSectionPositions: ArrayList<Int>? = null

    override fun getItemCount(): Int {
        return mDataArray?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_specie_index, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mDataArray!![position]
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
            val size = mDataArray!!.size
            while (i < size) {
                val section = mDataArray[i][0].toString().uppercase(Locale.getDefault())
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
}