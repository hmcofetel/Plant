package com.project.plantapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.project.plantapp.databinding.FragmentSpeciesBinding

class SpeciesFragment : Fragment() {
    private lateinit var binding:FragmentSpeciesBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSpeciesBinding.inflate(inflater, container, false)
        binding.speciesBackBnt.setOnClickListener{
            findNavController().navigate(R.id.mainProfileFragment)
        }
        // Inflate the layout for this fragment
        return binding.root
    }


}