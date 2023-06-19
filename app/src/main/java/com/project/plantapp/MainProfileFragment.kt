package com.project.plantapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.project.plantapp.databinding.FragmentProfileMainBinding

class MainProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileMainBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileMainBinding.inflate(inflater, container, false)
        binding.articlesNavBnt.setOnClickListener{
            findNavController().navigate(R.id.action_mainProfileFragment_to_articlesFragment)
        }

        binding.speciesNavBnt.setOnClickListener{
            findNavController().navigate(R.id.action_mainProfileFragment_to_speciesFragment)
        }

        binding.addingNewNavBnt.setOnClickListener{
            findNavController().navigate(R.id.action_mainProfileFragment_to_cameraFragment)
        }

        binding.avtBnt.setOnClickListener{
            findNavController().navigate(R.id.action_mainProfileFragment_to_detailProfileFragment)
        }
        // Inflate the layout for this fragment
        return binding.root
    }

}