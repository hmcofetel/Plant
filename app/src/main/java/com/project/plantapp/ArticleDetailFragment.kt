package com.project.plantapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.project.plantapp.databinding.FragmentArticleDetailBinding


class ArticleDetailFragment : Fragment() {
    private lateinit var  binding: FragmentArticleDetailBinding
    private val args : ArticleDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentArticleDetailBinding.inflate(inflater, container, false)
        binding.apply {
            articleDetailBackBnt.setOnClickListener{
                findNavController().navigate(R.id.articlesFragment)
            }
            tvArticleDetailTitle.text = args.title
            tvArticleDetailName.text = args.author
            tvDetailArticleDescription.text = args.description
            tvArticleDate.text = args.date

            Glide.with(ivArticleDetailAvatar.context).load(args.avt).centerCrop().into(ivArticleDetailAvatar)
            Glide.with(ivArticleDetailTitle.context).load(args.img).centerCrop().into(ivArticleDetailTitle)

        }

        return binding.root
    }


}
