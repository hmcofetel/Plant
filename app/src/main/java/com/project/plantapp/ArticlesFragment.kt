package com.project.plantapp

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.project.plantapp.adapter.ArticleAdapter
import com.project.plantapp.adapter.OnArticleItemListener
import com.project.plantapp.databinding.FragmentArticlesBinding
import com.project.plantapp.model.Articles
import com.project.plantapp.viewmodel.ArticlesVM

class ArticlesFragment : Fragment() {
    private lateinit var binding: FragmentArticlesBinding
    private lateinit var adapter: ArticleAdapter
    private lateinit var articlesVM: ArticlesVM

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

        binding = FragmentArticlesBinding.inflate(inflater, container, false)
        articlesVM = ViewModelProvider(this)[ArticlesVM::class.java]
        binding.articlesBackBnt.setOnClickListener{
            findNavController().navigate(R.id.mainProfileFragment)
        }
        requireActivity().findViewById<FloatingActionButton>(R.id.cameraAddNew).setOnClickListener{
            findNavController().navigate(R.id.cameraArcticles)
        }

        setUpRecyclerView()
        binding.rvArticles.adapter = adapter
        articlesVM.loadData()


        registerDataEvent()
        registerLoadingView()
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun setUpRecyclerView() {
        binding.rvArticles.layoutManager = LinearLayoutManager(context)
        adapter = ArticleAdapter(onImageClickListener)

        binding.searchArticles.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    adapter.filter.filter(newText)
                    return true
                }

            }
        )
    }

    private val onImageClickListener  = object : OnArticleItemListener {
        override fun onClickItem(item: Articles) {
            articlesVM.handleItemWhenClicked()
            binding.apply {
                val direction = ArticlesFragmentDirections.actionArticlesFragmentToArticleDetiailFragment(
                    item.title, item.author, item.description, item.img, item.date, item.id, item.like
                )
                findNavController().navigate(direction)
            }
        }

    }


    private fun registerDataEvent() {
        articlesVM.listOfArticles.observe(requireActivity()) { data ->
            run {
                adapter.submitList(data)
                adapter.unfilteredList = data
            }
        }
    }

    private fun registerLoadingView() {
        articlesVM.isLoading.observe(requireActivity()) { isLoading ->
            run {
                binding.progressBar.visibility =
                    if (isLoading) View.VISIBLE else
                        View.INVISIBLE
            }
        }
    }

}