package com.project.plantapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.navigation.fragment.findNavController
import com.project.plantapp.databinding.FragmentMySpeciesBinding


class MySpeciesFragment : Fragment() {
    private lateinit var binding: FragmentMySpeciesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMySpeciesBinding.inflate(inflater, container, false)
        binding.mySpeciesBackBnt.setOnClickListener{
            findNavController().navigate(R.id.detailProfileFragment)
            requireActivity().findViewById<CoordinatorLayout>(R.id.coordinatorLayout).visibility =View.VISIBLE
        }
        // Inflate the layout for this fragment
        return binding.root
    }


}