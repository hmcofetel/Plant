package com.project.plantapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.project.plantapp.databinding.FragmentProfileMainBinding
import com.project.plantapp.viewmodel.UserVM

class MainProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileMainBinding
    private lateinit var userVM: UserVM
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        userVM = ViewModelProvider(this)[UserVM::class.java]
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

        requireActivity().findViewById<FloatingActionButton>(R.id.cameraAddNew).setOnClickListener{
            findNavController().navigate(R.id.cameraFragment)
        }

        binding.tvUsernameHome.text = userVM.getProfile()?.get("name") as CharSequence?
        // Inflate the layout for this fragment
        return binding.root
    }

}