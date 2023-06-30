package com.project.plantapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.project.plantapp.databinding.FragmentProfileMainBinding
import com.project.plantapp.util.GlideApp
import com.project.plantapp.viewmodel.UserVM

class MainProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileMainBinding
    private val db = FirebaseStorage.getInstance().reference
    private lateinit var userVM: UserVM
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        userVM = ViewModelProvider(this)[UserVM::class.java]
        binding = FragmentProfileMainBinding.inflate(inflater, container, false)
        userVM.loadProfileStograte()

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

        userVM.isProfileEvent.observe(viewLifecycleOwner, Observer {
            binding.tvUsernameHome.text = it["last"] as String
            Log.v("hmcous " , it["avt"] as String)
            GlideApp.with(requireContext()).load(db.child("avatars").child(it["avt"] as String)).centerCrop().into(binding.imAvtHome)
        })

        // Inflate the layout for this fragment
        return binding.root
    }

}