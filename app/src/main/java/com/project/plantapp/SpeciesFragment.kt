package com.project.plantapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.plantapp.adapter.OnSpeciesItemListener
import com.project.plantapp.adapter.SpeciesAdapter
import com.project.plantapp.databinding.FragmentSpeciesCategoryBinding
import com.project.plantapp.model.Species
import com.project.plantapp.viewmodel.SpecieVM

class SpeciesFragment : Fragment() {
    private lateinit var binding: FragmentSpeciesCategoryBinding
    private lateinit var adapter: SpeciesAdapter
    private lateinit var viewModel: SpecieVM
    private val args : SpeciesFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSpeciesCategoryBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[SpecieVM::class.java]

        binding.speciesCategoryBackBnt.setOnClickListener{
            findNavController().navigate(R.id.speciesIndexFragment)
        }

        setUpRecyclerView()
        binding.rvSpecies.adapter = adapter
        binding.titleSpecies.text = args.category
        viewModel.loadData(args.category)

        registerDataEvent()

        return binding.root
    }

    private fun setUpRecyclerView() {
        binding.rvSpecies.layoutManager = LinearLayoutManager(context);
        adapter = SpeciesAdapter(onImageClickListener)
    }

    private val onImageClickListener  = object : OnSpeciesItemListener {
        override fun onClickItem(item: Species) {
            viewModel.handleItemWhenClicked()
            binding.apply {
//                val direction = ArticlesFragmentDirections.actionArticlesFragmentToArticleDetiailFragment(
//                    item.title, item.author, item.description, item.img, item.avt, item.date
//                )
//                findNavController().navigate()
            }
        }

    }


    private fun registerDataEvent() {
        viewModel.listOfArticles.observe(requireActivity(), Observer { data ->
            run {
                adapter.submitList(data)
            }
        })
    }




}