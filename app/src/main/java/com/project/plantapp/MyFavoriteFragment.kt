package com.project.plantapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.navigation.fragment.findNavController
import com.project.plantapp.databinding.FragmentMyFavoriteBinding

class MyFavoriteFragment : Fragment() {
    private lateinit var binding: FragmentMyFavoriteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
                requireActivity().findViewById<CoordinatorLayout>(R.id.coordinatorLayout).visibility =View.VISIBLE
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyFavoriteBinding.inflate(inflater, container, false)
        binding.favoriteBackBnt.setOnClickListener{
            findNavController().popBackStack()
            requireActivity().findViewById<CoordinatorLayout>(R.id.coordinatorLayout).visibility =View.VISIBLE
        }
        // Inflate the layout for this fragment
        return binding.root
    }

}