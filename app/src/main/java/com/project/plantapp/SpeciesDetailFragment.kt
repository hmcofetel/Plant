package com.project.plantapp

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.project.plantapp.databinding.FragmentSpeciesDetailBinding


class SpeciesDetailFragment : Fragment() {
    private lateinit var binding: FragmentSpeciesDetailBinding
    private val args : SpeciesDetailFragmentArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSpeciesDetailBinding.inflate(inflater, container, false)
        binding.apply {

            speciesDetailBackBnt.setOnClickListener{
                findNavController().popBackStack()
            }
            tvSpecieDetailTitle.text = args.title
            familyContentDetail.text = args.family
            kingdomContentDetail.text = args.kingdom
            descriptionContentDetail.text = args.description
            Glide.with(ivSpecieDetailTitle.context).load(args.image).centerCrop().into(ivSpecieDetailTitle)
        }
        // Inflate the layout for this fragment

        return binding.root
    }

}