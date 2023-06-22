package com.project.plantapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.plantapp.adapter.FavoriteArticlesAdapter
import com.project.plantapp.adapter.OnFavoriteArticleItemListener
import com.project.plantapp.databinding.FragmentMyFavoriteArticlesBinding
import com.project.plantapp.model.Articles
import com.project.plantapp.viewmodel.FavoriteAritclesVM


class MyFavoriteArticlesFragment : Fragment() {
    private lateinit var binding: FragmentMyFavoriteArticlesBinding
    private lateinit var adapter: FavoriteArticlesAdapter
    private lateinit var articlesVM: FavoriteAritclesVM

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
        binding = FragmentMyFavoriteArticlesBinding.inflate(inflater, container, false)
        articlesVM = ViewModelProvider(this)[FavoriteAritclesVM::class.java]
        binding.mySpeciesBackBnt.setOnClickListener {
            findNavController().popBackStack()
            requireActivity().findViewById<CoordinatorLayout>(R.id.coordinatorLayout).visibility =
                View.VISIBLE
        }

        setUpRecyclerView()
        binding.rvFavoriteArticles.adapter = adapter
        articlesVM.loadData()
        registerDataEvent()
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun setUpRecyclerView() {
        binding.rvFavoriteArticles.layoutManager = LinearLayoutManager(context)
        adapter = FavoriteArticlesAdapter(onImageClickListener)
    }

    private val onImageClickListener = object : OnFavoriteArticleItemListener {
        override fun onClickItem(item: Articles) {
            articlesVM.handleItemWhenClicked()
            binding.apply {
                val direction =
                    MyFavoriteArticlesFragmentDirections.actionMyFavoriteArticlesFragmentToArticleDetiailFragment(
                        item.title,
                        item.author,
                        item.description,
                        item.img,
                        item.date,
                        item.id,
                        item.like
                    )
                findNavController().navigate(direction)
            }
        }

    }

    private fun registerDataEvent() {
        articlesVM.listOfFavoriteArticles.observe(requireActivity(), Observer { data ->
            run {
                adapter.submitList(data)
            }
        })
    }

}