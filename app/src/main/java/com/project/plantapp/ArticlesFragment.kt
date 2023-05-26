package com.project.plantapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.plantapp.adapter.ArticleAdapter
import com.project.plantapp.adapter.OnArticleItemListener
import com.project.plantapp.databinding.FragmentArticlesBinding
import com.project.plantapp.model.Articles
import com.project.plantapp.viewmodel.ArticlesVM

class ArticlesFragment : Fragment() {
    private lateinit var binding: FragmentArticlesBinding
    private lateinit var adapter: ArticleAdapter
    private lateinit var viewModel: ArticlesVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArticlesBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[ArticlesVM::class.java]
        binding.articlesBackBnt.setOnClickListener{
            findNavController().navigate(R.id.mainProfileFragment)

        }

        setUpRecyclerView()
        viewModel.loadData()

        registerDataEvent()
        registerLoadingView()
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun setUpRecyclerView() {
        binding.rvArticles.layoutManager = LinearLayoutManager(context);

        adapter = ArticleAdapter(onImageClickListener)
        binding.rvArticles.adapter = adapter
    }

    private val onImageClickListener  = object : OnArticleItemListener {
        override fun onClickItem(item: Articles) {
            viewModel.handleItemWhenClicked()
            Toast.makeText(context, "on click item", Toast.LENGTH_SHORT).show();
        }


    }


    private fun registerDataEvent() {
        viewModel.listOfArticles.observe(requireActivity(), Observer { data ->
            run {
                adapter.submitList(data)
            }
        })
    }

    private fun registerLoadingView() {
        viewModel.isLoading.observe(requireActivity()) { isLoading ->
            run {
                binding.progressBar.visibility =
                    if (isLoading) View.VISIBLE else
                        View.INVISIBLE
            }
        }
    }

}