package com.project.plantapp

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class SplashFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Handler(Looper.getMainLooper()).postDelayed({
            findNavController().navigate(R.id.navigate_splashFragment_to_homeFragment)
        }, 3000)
        val view =  inflater.inflate(R.layout.fragment_splash, container, false)

        val animTop = AnimationUtils.loadAnimation(view.context,R.anim.from_top )
        val animBottom = AnimationUtils.loadAnimation(view.context,R.anim.from_bottom )

        val tvSplash = view.findViewById<ImageView>(R.id.splash_ico_title)
        val imgSplash = view.findViewById<ImageView>(R.id.splash_ico_image)

        tvSplash.animation = animBottom
        imgSplash.animation = animTop

        return view
    }


}