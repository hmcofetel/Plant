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
import com.project.plantapp.adapter.FavoriteSpeciesAdapter
import com.project.plantapp.adapter.OnSpeciesFavoriteItemListener
import com.project.plantapp.databinding.FragmentMyFavoriteSpeciesBinding
import com.project.plantapp.viewmodel.FavoriteSpeciesVM
import com.project.plantapp.model.Species

class MyFavoritePlantFragment : Fragment() {
    private lateinit var binding: FragmentMyFavoriteSpeciesBinding
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
        binding = FragmentMyFavoriteSpeciesBinding.inflate(inflater, container, false)
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
                    MyFavoritePlantFragmentDirections.actionMyFavoriteFragmentToSpeciesDetailFragment(
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