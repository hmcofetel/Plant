package com.project.plantapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.plantapp.adapter.ArticleAdapter
import com.project.plantapp.adapter.FavoriteSpeciesAdapter
import com.project.plantapp.adapter.OnArticleItemListener
import com.project.plantapp.adapter.OnSpeciesFavoriteItemListener
import com.project.plantapp.databinding.FragmentMyFavoriteBinding
import com.project.plantapp.model.Articles
import com.project.plantapp.model.FavoriteSpeciesVM
import com.project.plantapp.model.Species
import com.project.plantapp.viewmodel.ArticlesVM

class MyFavoriteFragment : Fragment() {
    private lateinit var binding: FragmentMyFavoriteBinding
    private lateinit var adapter: FavoriteSpeciesAdapter
    private lateinit var speciesVM: FavoriteSpeciesVM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
                requireActivity().findViewById<CoordinatorLayout>(R.id.coordinatorLayout).visibility =
                    View.VISIBLE
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyFavoriteBinding.inflate(inflater, container, false)
        speciesVM = ViewModelProvider(this)[FavoriteSpeciesVM::class.java]
        binding.favoriteBackBnt.setOnClickListener {
            findNavController().popBackStack()
            requireActivity().findViewById<CoordinatorLayout>(R.id.coordinatorLayout).visibility =
                View.VISIBLE
        }

        setUpRecyclerView()
        binding.rvFavoriteSpecies.adapter = adapter
        speciesVM.loadData()
        registerDataEvent()
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun setUpRecyclerView() {
        binding.rvFavoriteSpecies.layoutManager = GridLayoutManager(context, 2)
        adapter = FavoriteSpeciesAdapter(onImageClickListener)
    }

    private val onImageClickListener = object : OnSpeciesFavoriteItemListener {
        override fun onClickItem(item: Species) {
            speciesVM.handleItemWhenClicked()
            binding.apply {
                val direction =
                    MyFavoriteFragmentDirections.actionMyFavoriteFragmentToSpeciesDetailFragment(
                        item.title,
                        item.family,
                        item.kingdom,
                        item.description,
                        false,
                        item.image,
                        item.id

                    )
                findNavController().navigate(direction)
            }
        }

    }


    private fun registerDataEvent() {
        speciesVM.listOfSpecies.observe(requireActivity(), Observer { data ->
            run {
                adapter.submitList(data)
            }
        })
    }

//    private fun registerLoadingView() {
//        articlesVM.isLoading.observe(requireActivity()) { isLoading ->
//            run {
//                binding.progressBar.visibility =
//                    if (isLoading) View.VISIBLE else
//                        View.INVISIBLE
//            }
//        }
//    }

}