package com.project.plantapp

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.plantapp.adapter.SpecieIndexAdapter
import com.project.plantapp.data.DataIndexSpecies
import com.project.plantapp.databinding.FragmentSpeciesBinding
import java.util.Objects

class SpeciesFragment : Fragment() {
    private lateinit var binding:FragmentSpeciesBinding
    private val mData by lazy { DataIndexSpecies.getAlphabetFullData() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSpeciesBinding.inflate(inflater, container, false)
        binding.speciesBackBnt.setOnClickListener{
            findNavController().navigate(R.id.mainProfileFragment)
        }
        binding.rvSpecies.apply {
            adapter = SpecieIndexAdapter(mData)
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


}