package com.project.plantapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.project.plantapp.databinding.FragmentArticlesBinding

class ArticlesFragment : Fragment() {
    private lateinit var binding: FragmentArticlesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArticlesBinding.inflate(inflater, container, false)
        binding.articlesBackBnt.setOnClickListener{
            findNavController().navigate(R.id.mainProfileFragment)

        }
        // Inflate the layout for this fragment
        return binding.root
    }


}