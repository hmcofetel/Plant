package com.project.plantapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.navigation.fragment.findNavController
import com.project.plantapp.databinding.FragmentMyArticlesBinding

class MyArticlesFragment : Fragment() {
    private lateinit var binding: FragmentMyArticlesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyArticlesBinding.inflate(inflater, container, false)
        binding.myArticlesBackBnt.setOnClickListener{
            findNavController().navigate(R.id.detailProfileFragment)
            requireActivity().findViewById<CoordinatorLayout>(R.id.coordinatorLayout).visibility =View.VISIBLE
        }
        // Inflate the layout for this fragment
        return binding.root
    }


}