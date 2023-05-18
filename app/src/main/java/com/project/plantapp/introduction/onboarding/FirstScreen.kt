package com.project.plantapp.introduction.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.button.MaterialButton
import com.project.plantapp.R

class FirstScreen : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_on_boarding_first_screen, container, false)

        val next = view.findViewById<MaterialButton>(R.id.onboardingFirstScreenBnt)
        val viewPager =  activity?.findViewById<ViewPager2>(R.id.view_paper)
        next.setOnClickListener{
            viewPager?.currentItem = 1
        }

        return view
    }

}