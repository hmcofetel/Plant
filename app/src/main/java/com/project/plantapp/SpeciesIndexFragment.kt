package com.project.plantapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.plantapp.adapter.OnSpecieSIndexItemListener
import com.project.plantapp.adapter.SpecieIndexAdapter
import com.project.plantapp.data.DataSpecies
import com.project.plantapp.databinding.FragmentIndexSpeciesBinding
import java.util.Objects

class SpeciesIndexFragment : Fragment() {
    private lateinit var binding:FragmentIndexSpeciesBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentIndexSpeciesBinding.inflate(inflater, container, false)
        binding.speciesBackBnt.setOnClickListener{
            findNavController().navigate(R.id.mainProfileFragment)
        }
        binding.rvSpecies.apply {
            adapter = SpecieIndexAdapter(DataSpecies.getCategoryList(),onSpecieItemListener)
            layoutManager = LinearLayoutManager(context)
            setIndexBarTransparentValue(0.0.toFloat())
            setIndexBarTextColor("#A1A8B9")
            setIndexbarHorizontalMargin(20.toFloat())
            setIndexBarStrokeVisibility(false)

        }

        Objects.requireNonNull<RecyclerView.LayoutManager>(binding.rvSpecies.layoutManager)
            .scrollToPosition(0)
        // Inflate the layout for this fragment
        return binding.root
    }


    private val onSpecieItemListener  = object : OnSpecieSIndexItemListener {
        override fun onClickItem(item: String) {
            val direction  = SpeciesIndexFragmentDirections.actionSpeciesFragmentToSpeciesCategoryFragment(item)
            findNavController().navigate(direction)
        }
    }


}